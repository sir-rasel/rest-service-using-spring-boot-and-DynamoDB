package com.sir.todorestservicewithdynamodb.service.Impl;

import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.LoginRequestDto;
import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.RefreshTokenDto;
import com.sir.todorestservicewithdynamodb.dtos.loginSignup.request.SignupRequestDto;
import com.sir.todorestservicewithdynamodb.dtos.loginSignup.response.LoginResponseDto;
import com.sir.todorestservicewithdynamodb.dtos.user.UserDto;
import com.sir.todorestservicewithdynamodb.exception.ApplicationException;
import com.sir.todorestservicewithdynamodb.model.UserEntity;
import com.sir.todorestservicewithdynamodb.repository.UserRepository;
import com.sir.todorestservicewithdynamodb.security.CustomEncoder;
import com.sir.todorestservicewithdynamodb.service.UserService;
import com.sir.todorestservicewithdynamodb.utility.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();

    private final JWTUtil jwtUtil;
    private final CustomEncoder passwordEncoder;

    @Override
    public Mono<ResponseEntity<?>> validateLoginRequestAndGetTokenResponse(LoginRequestDto request) {
        return findUserByUserName(request.getUserEmail())
                .map(userDetails -> {
                    if (passwordEncoder.encode(request.getPassword()).equals(userDetails.getPassword())) {
                        return ResponseEntity.ok(generateTokenResponseOnLogin(userDetails));
                    } else {
                        log.info("Password not matched");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private Mono<LoginResponseDto> generateTokenResponseOnLogin(UserEntity userDetails) {
        String token = jwtUtil.generateToken(userDetails);
        Date tokenExpirationDate = jwtUtil.getExpirationDateFromToken(token);

        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        Date refreshTokenExpirationDate = jwtUtil.getExpirationDateFromToken(refreshToken);

        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .token(token)
                .tokenExpirationDate(tokenExpirationDate)
                .refreshToken(refreshToken)
                .refreshTokenExpirationDate(refreshTokenExpirationDate)
                .build();

        return updateUserRefreshToken(userDetails, refreshToken)
                .map(userEntity -> loginResponse);
    }

    private Mono<UserEntity> findUserByUserName(String userEmail) {
        Mono<UserEntity> user = userRepository.findByEmail(userEmail);
        return user.switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<ResponseEntity<?>> createUserOnSignup(SignupRequestDto userdata) {
        userdata.setPassword(passwordEncoder.encode(userdata.getPassword()));
        return Mono.just(ResponseEntity.ok(createUser(userdata)));
    }

    @Override
    public Mono<ResponseEntity<?>> refreshTokenAndGetTokenResponse(RefreshTokenDto request) {
        try {
            String userEmail = jwtUtil.getUserEmailFromToken(request.getRefreshToken());
            return findUserByUserName(userEmail)
                    .filter(userEntity -> userEntity.getRefreshToken().equals(request.getRefreshToken()))
                    .switchIfEmpty(Mono.error(new ApplicationException("Invalid refresh token")))
                    .map(userDetails -> ResponseEntity.ok(generateTokenResponseOnLogin(userDetails)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Mono.error(new ApplicationException("Invalid refresh token"));
        }
    }

    private Mono<UserDto> createUser(SignupRequestDto userData) {
        UserEntity user = UserEntity.builder()
                .name(userData.getName())
                .password(userData.getPassword())
                .email(userData.getEmail())
                .roles(userData.getRoles())
                .refreshToken(null)
                .build();

        return userRepository.save(user)
                .map(userEntity -> mapper.map(userEntity, UserDto.class))
                .doOnError(throwable -> log.error("Error db: ", throwable));
    }

    private Mono<UserEntity> updateUserRefreshToken(UserEntity user, String refreshToken) {
        user.setRefreshToken(refreshToken);

        return userRepository.save(user).map(userEntity -> userEntity);
    }
}
