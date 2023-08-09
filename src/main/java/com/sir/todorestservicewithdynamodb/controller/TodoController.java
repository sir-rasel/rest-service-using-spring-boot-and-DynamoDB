package com.sir.todorestservicewithdynamodb.controller;

import com.sir.todorestservicewithdynamodb.constant.TodoStatus;
import com.sir.todorestservicewithdynamodb.dtos.todo.TodoDto;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoCreateRequest;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoUpdateRequest;
import com.sir.todorestservicewithdynamodb.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/api/todos",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/{email}")
    public Flux<TodoDto> getAllTodos(@PathVariable final String email) {
        return todoService.getAllTodos(email);
    }

    @GetMapping("/{email}")
    public Flux<TodoDto> getAllTodosByStatus(@PathVariable final String email,
                                             @RequestParam @NotEmpty TodoStatus status) {
        return todoService.getAllTodosByStatus(email, status);
    }

    @GetMapping("/{id}")
    public Mono<TodoDto> getTodo(@PathVariable final String id) {
        return todoService.getTodo(id);
    }

    @PostMapping
    public Mono<TodoDto> createTodo(@RequestBody @Valid final TodoCreateRequest todoCreateRequest) {
        return todoService.createTodo(todoCreateRequest);
    }

    @PutMapping("/{id}")
    public Mono<TodoDto> updateTodo(@PathVariable final String id,
                                    @RequestBody @Valid final TodoUpdateRequest todoUpdateRequest) {
        return todoService.updateTodo(id, todoUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTodo(@PathVariable final String id) {
        return todoService.deleteTodo(id);
    }
}
