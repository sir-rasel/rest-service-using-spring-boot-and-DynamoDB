package com.sir.todorestservicewithdynamodb.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String firstName;
    private String email;
    private List<@NotBlank(message = "Role should not be empty") String> roles;
}
