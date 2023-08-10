package com.sir.todorestservicewithdynamodb.dtos.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {
    private String id;
    private String userEmail;
    private String createdAt;
    private String taskDescription;
    private String status;
    private String startedAt;
    private String completedAt;
    public String finishedBefore;
}
