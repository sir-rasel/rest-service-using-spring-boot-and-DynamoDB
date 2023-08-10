package com.sir.todorestservicewithdynamodb.dtos.todo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TodoCreateRequest {
    @NotBlank
    @Email
    public String userEmail;

    @NotBlank
    public String taskDescription;
}
