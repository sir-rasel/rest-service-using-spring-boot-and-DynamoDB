package com.sir.todorestservicewithdynamodb.repository.Impl;

import com.sir.todorestservicewithdynamodb.annotation.DynamoDbEntityDeclaration;
import com.sir.todorestservicewithdynamodb.exception.ApplicationException;
import com.sir.todorestservicewithdynamodb.repository.GenericRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.utils.StringUtils;

import java.util.Map;

@Repository
public abstract class GenericRepositoryImpl<T> implements GenericRepository<T> {
    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    protected final DynamoDbAsyncTable<T> dynamoDbEnhancedAsyncTable;
    protected final DynamoDbAsyncIndex<T> dynamoDbAsyncIndex;
    protected final Class<T> entityType;
    protected final String tableName;
    protected final String gsiIndexName;

    protected GenericRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, Class<T> entityType) {
        this.dynamoDbEnhancedAsyncClient = dynamoDbEnhancedAsyncClient;
        this.entityType = entityType;
        this.tableName = getTableName();
        this.gsiIndexName = getGsiIndexName();
        this.dynamoDbEnhancedAsyncTable = createAsyncTable();
        this.dynamoDbAsyncIndex = createGsi1AsyncIndex();
    }

    private DynamoDbAsyncTable<T> createAsyncTable() {
        return this.dynamoDbEnhancedAsyncClient.table(this.tableName, TableSchema.fromBean(this.entityType));
    }

    private DynamoDbAsyncIndex<T> createGsi1AsyncIndex() {
        if (StringUtils.isBlank(this.gsiIndexName)) {
            return null;
        }
        return this.dynamoDbEnhancedAsyncTable.index(this.gsiIndexName);
    }

    private String getTableName() {
        if (this.entityType.isAnnotationPresent(DynamoDbEntityDeclaration.class)) {
            DynamoDbEntityDeclaration dynamoDbEntityDeclaration =
                    entityType.getAnnotation(DynamoDbEntityDeclaration.class);
            if (dynamoDbEntityDeclaration.tableName() == null) {
                throw new ApplicationException("Table Name not found");
            }

            return dynamoDbEntityDeclaration.tableName();
        }

        throw new ApplicationException("Table Name not found");
    }

    private String getGsiIndexName() {
        if (this.entityType.isAnnotationPresent(DynamoDbEntityDeclaration.class)) {
            DynamoDbEntityDeclaration dynamoDbEntityDeclaration =
                    entityType.getAnnotation(DynamoDbEntityDeclaration.class);
            return dynamoDbEntityDeclaration.gsi();
        }

        return null;
    }

    @Override
    public Mono<T> findById(String primaryKey) {
        Key key = Key.builder()
                .partitionValue(primaryKey)
                .build();
        return Mono.fromFuture(() -> this.dynamoDbEnhancedAsyncTable.getItem(key));
    }

    @Override
    public Flux<T> findAllById(String primaryKey) {
        Key key = Key.builder()
                .partitionValue(primaryKey)
                .build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(key);
        QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .build();

        return Mono.from(dynamoDbEnhancedAsyncTable.query(queryEnhancedRequest))
                .mapNotNull(Page::items)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<T> findByIdAndSk(String primaryKey, String secondaryKey) {
        Key key = Key.builder()
                .partitionValue(primaryKey)
                .sortValue(secondaryKey)
                .build();
        return Mono.fromFuture(() -> this.dynamoDbEnhancedAsyncTable.getItem(key));
    }

    @Override
    public Mono<Page<T>> findAll(@Nullable Map<String, AttributeValue> keys, Integer pageSize) {
        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                .limit(pageSize)
                .exclusiveStartKey(keys)
                .build();

        return Mono.from(this.dynamoDbEnhancedAsyncTable.scan(scanEnhancedRequest));
    }

    @Override
    public Mono<T> save(T entity) {
        return Mono.fromFuture(() -> this.dynamoDbEnhancedAsyncTable.putItem(entity))
                .thenReturn(entity);
    }

    @Override
    public Mono<T> update(T entity) {
        return Mono.fromFuture(() -> this.dynamoDbEnhancedAsyncTable.updateItem(entity));
    }

    @Override
    public Mono<T> deleteById(String primaryKey) {
        Key key = Key.builder()
                .partitionValue(primaryKey)
                .build();
        return Mono.fromFuture(() -> this.dynamoDbEnhancedAsyncTable.deleteItem(key));
    }

    @Override
    public Mono<T> deleteByIdAndSortKey(String primaryKey, String secondaryKey) {
        Key key = Key.builder()
                .partitionValue(primaryKey)
                .sortValue(secondaryKey)
                .build();
        return Mono.fromFuture(() -> this.dynamoDbEnhancedAsyncTable.deleteItem(key));
    }

    @Override
    public Mono<T> findByIdFromGSI(String primaryKey) {
        return Mono.fromSupplier(() -> this.dynamoDbAsyncIndex)
                .switchIfEmpty(Mono.error(() -> new ApplicationException("No GSI found")))
                .flatMap(index -> getItemFromGsiByPk(primaryKey));
    }

    private Mono<T> getItemFromGsiByPk(String partitionKey) {
        return getItemListFromGsiByPk(partitionKey)
                .mapNotNull(tPage -> tPage.items().stream().findFirst().orElse(null));
    }

    private Mono<Page<T>> getItemListFromGsiByPk(String partitionKey) {
        Key searchKey = Key.builder().partitionValue(partitionKey).build();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(searchKey);
        QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .build();

        return Mono.from(dynamoDbAsyncIndex.query(queryEnhancedRequest));
    }

    @Override
    public Mono<Page<T>> findAllFromGSI(String primaryKey, @Nullable Map<String, AttributeValue> keys, Integer pageSize) {
        return Mono.fromSupplier(() -> this.dynamoDbAsyncIndex)
                .switchIfEmpty(Mono.error(() -> new ApplicationException("No GSI found")))
                .flatMap(unusedDynamoDbAsyncIndex -> getItemListFromGsiByPk(primaryKey));
    }
}
