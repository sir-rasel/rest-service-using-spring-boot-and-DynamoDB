package com.sir.todorestservicewithdynamodb.repository;

import com.sir.todorestservicewithdynamodb.model.ToDoEntity;
import reactor.core.publisher.Mono;

public interface ToDoRepository extends GenericRepository<ToDoEntity> {
    Mono<ToDoEntity> findByEmailAndId(String email, String id);
}
