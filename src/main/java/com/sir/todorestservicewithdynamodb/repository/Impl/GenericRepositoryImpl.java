package com.sir.todorestservicewithdynamodb.repository.Impl;

import com.sir.todorestservicewithdynamodb.repository.GenericRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Repository
public class GenericRepositoryImpl<T> implements GenericRepository<T> {
    private final DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
    private final ConfigurableListableBeanFactory configurableListableBeanFactory;

    protected final DynamoDbAsyncTable<T> dynamoDbAsyncTable;
    protected final DynamoDbAsyncIndex<T> dynamoDbAsyncIndex1;
    protected final Class<T> entityType;

    protected final String tableName;
    protected final String gsi_1_tableName;

    public GenericRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient,
                                 ConfigurableListableBeanFactory configurableListableBeanFactory) {
        this.dynamoDbEnhancedAsyncClient = dynamoDbEnhancedAsyncClient;
        this.configurableListableBeanFactory = configurableListableBeanFactory;
        this.dynamoDbAsyncTable = dynamoDbAsyncTable;
        this.dynamoDbAsyncIndex1 = dynamoDbAsyncIndex1;
        this.tableName = tableName;
        this.gsi_1_tableName = gsi_1_tableName;
    }

    @Override
    public Mono<T> findById(String primaryKey) {
        return null;
    }

    @Override
    public Mono<T> findByIdAndSk(String primaryKey, String secondaryKey) {
        return null;
    }

    @Override
    public Mono<Page<T>> findAll(@Nullable Map<String, AttributeValue> keys, Integer pageSize) {
        return null;
    }

    @Override
    public Mono<T> save(T entity) {
        return null;
    }

    @Override
    public Mono<T> update(T entity) {
        return null;
    }

    @Override
    public Mono<T> deleteById(String primaryKey) {
        return null;
    }

    @Override
    public Mono<T> findByIdFromGSI_1(String primaryKey) {
        return null;
    }

    @Override
    public Mono<Page<T>> findAllFromGSI_1(String primaryKey, @Nullable Map<String, AttributeValue> keys, Integer pageSize) {
        return null;
    }
}
