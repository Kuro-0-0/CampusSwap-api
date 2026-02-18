package com.salesianostriana.dam.campusswap.seguridad.auth;

import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginRequest;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginResponse;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.RegisterRequest;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> doLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(201)
                .body(authService.doLogin(loginRequest));

    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> doRegister(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(201)
                .body(authService.doRegister(registerRequest));

    }
}
