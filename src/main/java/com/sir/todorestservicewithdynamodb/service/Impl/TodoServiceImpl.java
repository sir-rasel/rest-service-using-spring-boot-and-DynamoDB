package com.sir.todorestservicewithdynamodb.service.Impl;

import com.sir.todorestservicewithdynamodb.repository.ToDoRepository;
import com.sir.todorestservicewithdynamodb.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final ToDoRepository toDoRepository;

    
}
