package com.sir.todorestservicewithdynamodb.controller;

import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.LoginRequestDto;
import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.RefreshTokenDto;
import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.SignupRequestDto;
import com.sir.todorestservicewithdynamodb.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api")
public class LoginSignupController {
    private UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody @Valid LoginRequestDto request) {
        return userService.validateLoginRequestAndGetTokenResponse(request);
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<?>> createUser(@RequestBody @Valid SignupRequestDto user) {
        return userService.createUserOnSignup(user);
    }

    @PostMapping("/refresh-token")
    public Mono<ResponseEntity<?>> refreshToken(@RequestBody @Valid RefreshTokenDto request) {
        return userService.refreshTokenAndGetTokenResponse(request);
    }
}
