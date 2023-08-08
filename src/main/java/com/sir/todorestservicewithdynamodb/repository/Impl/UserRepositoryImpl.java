package com.sir.todorestservicewithdynamodb.repository.Impl;

import com.sir.todorestservicewithdynamodb.model.UserEntity;
import com.sir.todorestservicewithdynamodb.repository.UserRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<UserEntity> implements UserRepository {
    public UserRepositoryImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        super(dynamoDbEnhancedAsyncClient, UserEntity.class);
    }

    @Override
    public Mono<UserEntity> findByEmail(String email) {
        return findById(email);
    }
}
