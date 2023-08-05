package com.sir.todorestservicewithdynamodb.dtos.loginSignup.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String password;
}
