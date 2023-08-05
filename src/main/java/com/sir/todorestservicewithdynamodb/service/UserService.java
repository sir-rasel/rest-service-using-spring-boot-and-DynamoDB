package com.sir.todorestservicewithdynamodb.service;

import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.LoginRequestDto;
import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.RefreshTokenDto;
import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.SignupRequestDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<ResponseEntity<?>> validateLoginRequestAndGetTokenResponse(LoginRequestDto request);

    Mono<ResponseEntity<?>> createUserOnSignup(SignupRequestDto userdata);

    Mono<ResponseEntity<?>> refreshTokenAndGetTokenResponse(RefreshTokenDto request);
}
