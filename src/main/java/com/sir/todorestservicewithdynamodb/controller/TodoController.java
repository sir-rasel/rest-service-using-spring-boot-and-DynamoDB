package com.sir.todorestservicewithdynamodb.controller;

import com.sir.todorestservicewithdynamodb.dtos.todo.TodoDto;
import com.sir.todorestservicewithdynamodb.service.TodoService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/todo/{id}")
    public Mono<TodoDto> getTodoById(@PathVariable @NotEmpty String id) {
        return Mono.empty();
    }
}
