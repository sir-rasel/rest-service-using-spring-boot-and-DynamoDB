package com.sir.todorestservicewithdynamodb.service;

import com.sir.todorestservicewithdynamodb.constant.TodoStatus;
import com.sir.todorestservicewithdynamodb.dtos.todo.TodoDto;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoCreateRequest;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoService {
    Flux<TodoDto> getAllTodos(final String email);

    Flux<TodoDto> getAllTodosByStatus(final String email, final TodoStatus status);

    Mono<TodoDto> getTodo(final String id);

    Mono<TodoDto> createTodo(final TodoCreateRequest todoCreateRequest);

    Mono<TodoDto> updateTodo(final String id, final TodoUpdateRequest todoUpdateRequest);

    Mono<Void> deleteTodo(final String id);
}
