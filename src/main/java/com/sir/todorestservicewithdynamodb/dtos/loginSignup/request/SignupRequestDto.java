package com.sir.todorestservicewithdynamodb.dtos.loginSignup.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotBlank(message = "Password should not be empty")
    private String password;

    @NotBlank
    @Email(message = "Invalid email address")
    private String email;
    
    @NotEmpty(message = "Roles should not be empty")
    private List<@NotBlank(message = "Role should not be empty") String> roles;
}
