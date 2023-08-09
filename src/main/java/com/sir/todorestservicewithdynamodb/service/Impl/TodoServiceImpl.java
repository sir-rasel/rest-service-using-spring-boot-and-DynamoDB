package com.sir.todorestservicewithdynamodb.service.Impl;

import com.sir.todorestservicewithdynamodb.constant.TodoStatus;
import com.sir.todorestservicewithdynamodb.dtos.todo.TodoDto;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoCreateRequest;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoUpdateRequest;
import com.sir.todorestservicewithdynamodb.repository.ToDoRepository;
import com.sir.todorestservicewithdynamodb.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final ToDoRepository toDoRepository;

    @Override
    public Flux<TodoDto> getAllTodos(String email) {
        return null;
    }

    @Override
    public Flux<TodoDto> getAllTodosByStatus(String email, TodoStatus status) {
        return null;
    }

    @Override
    public Mono<TodoDto> getTodo(String id) {
        return null;
    }

    @Override
    public Mono<TodoDto> createTodo(TodoCreateRequest todoCreateRequest) {
        return null;
    }

    @Override
    public Mono<TodoDto> updateTodo(String id, TodoUpdateRequest todoUpdateRequest) {
        return null;
    }

    @Override
    public Mono<Void> deleteTodo(String id) {
        return null;
    }
}
