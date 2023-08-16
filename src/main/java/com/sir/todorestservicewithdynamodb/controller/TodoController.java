package com.sir.todorestservicewithdynamodb.controller;

import com.sir.todorestservicewithdynamodb.constant.TodoStatus;
import com.sir.todorestservicewithdynamodb.dtos.todo.TodoDto;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoCreateRequest;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoUpdateRequest;
import com.sir.todorestservicewithdynamodb.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/api/todos",
        produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/email/{email}")
    public Flux<TodoDto> getAllTodos(@PathVariable final String email) {
        return todoService.getAllTodos(email);
    }

    @GetMapping("/{email}/by-status/{status}")
    public Flux<TodoDto> getAllTodosByStatus(@PathVariable final String email,
                                             @PathVariable final TodoStatus status) {
        return todoService.getAllTodosByStatus(email, status);
    }

    @GetMapping("/id/{id}")
    public Mono<TodoDto> getTodo(@PathVariable final String id) {
        return todoService.getTodo(id);
    }

    @PostMapping("/add")
    public Mono<TodoDto> createTodo(@RequestBody @Valid TodoCreateRequest todoCreateRequest) {
        return todoService.createTodo(todoCreateRequest);
    }

    @PutMapping("/{id}")
    public Mono<TodoDto> updateTodo(@PathVariable final String id,
                                    @RequestBody @Valid TodoUpdateRequest todoUpdateRequest) {
        return todoService.updateTodo(id, todoUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTodo(@PathVariable final String id) {
        return todoService.deleteTodo(id);
    }
}
