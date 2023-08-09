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
    public String id;
    public String userEmail;
    public String createdAt;
    public String taskDescription;
    public String status;
    public String startedAt;
    public String completedAt;
}
