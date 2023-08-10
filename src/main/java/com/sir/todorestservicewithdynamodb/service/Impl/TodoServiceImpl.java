package com.sir.todorestservicewithdynamodb.service.Impl;

import com.sir.todorestservicewithdynamodb.constant.TodoStatus;
import com.sir.todorestservicewithdynamodb.dtos.todo.TodoDto;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoCreateRequest;
import com.sir.todorestservicewithdynamodb.dtos.todo.request.TodoUpdateRequest;
import com.sir.todorestservicewithdynamodb.model.ToDoEntity;
import com.sir.todorestservicewithdynamodb.repository.ToDoRepository;
import com.sir.todorestservicewithdynamodb.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final ToDoRepository toDoRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Flux<TodoDto> getAllTodos(String email) {
        return toDoRepository.findAllById(email)
                .map(items -> mapper.map(items, TodoDto.class));
    }

    @Override
    public Flux<TodoDto> getAllTodosByStatus(String email, TodoStatus status) {
        return toDoRepository.getTodoByStatus(email, status)
                .mapNotNull(toDoEntity -> mapper.map(toDoEntity, TodoDto.class));
    }

    @Override
    public Mono<TodoDto> getTodo(String id) {
        return toDoRepository.getTodoById(id)
                .mapNotNull(toDoEntity -> mapper.map(toDoEntity, TodoDto.class));
    }

    @Override
    public Mono<TodoDto> createTodo(TodoCreateRequest todoCreateRequest) {
        return toDoRepository.save(ToDoEntity.builder()
                        .userEmail(todoCreateRequest.userEmail)
                        .taskDescription(todoCreateRequest.taskDescription)
                        .id(UUID.randomUUID().toString())
                        .createdAt(Instant.now().toString())
                        .status(TodoStatus.IN_COMPLETE.getStatus())
                        .build())
                .mapNotNull(toDoEntity -> mapper.map(toDoEntity, TodoDto.class));
    }

    @Override
    public Mono<TodoDto> updateTodo(String id, TodoUpdateRequest todoUpdateRequest) {
        return toDoRepository.getTodoById(id)
                .flatMap(toDoEntity -> toDoRepository.update(ToDoEntity.builder()
                        .userEmail(toDoEntity.getUserEmail())
                        .taskDescription(todoUpdateRequest.taskDescription)
                        .startedAt(todoUpdateRequest.startedAt)
                        .status(todoUpdateRequest.status)
                        .createdAt(todoUpdateRequest.createdAt)
                        .id(toDoEntity.getId())
                        .completedAt(todoUpdateRequest.completedAt)
                        .finishedBefore(todoUpdateRequest.finishedBefore)
                        .build()))
                .mapNotNull(toDoEntity -> mapper.map(toDoEntity, TodoDto.class));
    }

    @Override
    public Mono<Void> deleteTodo(String id) {
        return getTodo(id)
                .mapNotNull(todoDto -> toDoRepository.deleteById(todoDto.getUserEmail()))
                .flatMap(todo -> Mono.empty());
    }
}
