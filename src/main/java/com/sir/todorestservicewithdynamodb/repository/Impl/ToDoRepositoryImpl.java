package com.sir.todorestservicewithdynamodb.repository.Impl;

import com.sir.todorestservicewithdynamodb.constant.TodoStatus;
import com.sir.todorestservicewithdynamodb.constant.TodoTableIndexNames;
import com.sir.todorestservicewithdynamodb.model.ToDoEntity;
import com.sir.todorestservicewithdynamodb.repository.ToDoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Repository
public class ToDoRepositoryImpl extends GenericRepositoryImpl<ToDoEntity> implements ToDoRepository {
    public ToDoRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        super(dynamoDbEnhancedAsyncClient, ToDoEntity.class);
    }

    @Override
    public Mono<ToDoEntity> findByEmailAndId(String email, String id) {
        return findById(email);
    }

    @Override
    public Mono<ToDoEntity> getTodoById(String id) {
        Key searchKey = Key.builder()
                .partitionValue(id)
                .build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(searchKey);

        SdkPublisher<Page<ToDoEntity>> publisher
                = dynamoDbEnhancedAsyncTable.index(TodoTableIndexNames.ID_INDEX)
                .query(queryConditional);

        return Mono.from(publisher)
                .mapNotNull(this::getFirstElementFromPage);
    }

    @Override
    public Flux<ToDoEntity> getTodoByStatus(String email, TodoStatus status) {
        Key searchKey = Key.builder()
                .partitionValue(status.getStatus())
                .sortValue(email)
                .build();

        QueryConditional queryConditional = QueryConditional.keyEqualTo(searchKey);

        SdkPublisher<Page<ToDoEntity>> publisher
                = dynamoDbEnhancedAsyncTable.index(TodoTableIndexNames.TODO_STATUS_INDEX)
                .query(queryConditional);

        return Mono.from(publisher)
                .mapNotNull(Page::items)
                .flatMapMany(Flux::fromIterable);
    }

    private ToDoEntity getFirstElementFromPage(Page<ToDoEntity> toDoEntityPage) {
        return toDoEntityPage.items().stream()
                .findFirst()
                .orElse(null);
    }
}