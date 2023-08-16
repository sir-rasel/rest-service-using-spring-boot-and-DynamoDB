package com.sir.todorestservicewithdynamodb.security;

import com.sir.todorestservicewithdynamodb.constant.Roles;
import com.sir.todorestservicewithdynamodb.utility.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.fromCallable(() -> {
            String authToken = authentication.getCredentials().toString();
            String userEmail;

            try {
                userEmail = jwtUtil.getUserEmailFromToken(authToken);
            } catch (Exception e) {
                userEmail = null;
            }

            if (userEmail != null && jwtUtil.validateToken(authToken)) {
                Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
                List<String> rolesMap = claims.get("roles", List.class);
                List<String> roles = new ArrayList<>();

                for (String rolemap : rolesMap) {
                    roles.add("ROLE_" + Roles.valueOf(rolemap));
                }
                return new UsernamePasswordAuthenticationToken(userEmail, authToken,
                        roles.stream().map(SimpleGrantedAuthority::new).toList()
                );
            }

            return null;
        });
    }
}
