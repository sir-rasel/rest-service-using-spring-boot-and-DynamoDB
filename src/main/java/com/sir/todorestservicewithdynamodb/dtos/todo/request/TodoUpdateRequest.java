package com.sir.todorestservicewithdynamodb.dtos.todo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TodoUpdateRequest {
    public String createdAt = null;
    public String taskDescription = null;
    public String status = null;
    public String startedAt = null;
    public String completedAt = null;
    public String finishedBefore = null;
}
