package com.sir.todorestservicewithdynamodb.repository.Impl;

import com.sir.todorestservicewithdynamodb.model.ToDoEntity;
import com.sir.todorestservicewithdynamodb.repository.ToDoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

@Repository
public class ToDoRepositoryImpl extends GenericRepositoryImpl<ToDoEntity> implements ToDoRepository {
    public ToDoRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        super(dynamoDbEnhancedAsyncClient, ToDoEntity.class);
    }

    @Override
    public Mono<ToDoEntity> findByEmailAndId(String email, String id) {
        return findById(email);
    }
}