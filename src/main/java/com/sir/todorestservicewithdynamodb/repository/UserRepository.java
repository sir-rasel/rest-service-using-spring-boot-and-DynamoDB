package com.sir.todorestservicewithdynamodb.repository;

import com.sir.todorestservicewithdynamodb.model.UserEntity;
import reactor.core.publisher.Mono;

public interface UserRepository extends GenericRepository<UserEntity> {
    Mono<UserEntity> findByEmail(String email);
}
