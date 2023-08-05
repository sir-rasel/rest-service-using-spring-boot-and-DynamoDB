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
    public String name;
    public String firstName;
    public String email;
    public List<@NotBlank(message = "Role should not be empty") String> roles;
}
