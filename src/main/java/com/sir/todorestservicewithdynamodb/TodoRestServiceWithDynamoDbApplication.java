package com.sir.todorestservicewithdynamodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class TodoRestServiceWithDynamoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoRestServiceWithDynamoDbApplication.class, args);
    }

}
