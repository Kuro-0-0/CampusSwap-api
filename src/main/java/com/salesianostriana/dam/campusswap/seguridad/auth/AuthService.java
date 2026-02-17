package com.salesianostriana.dam.campusswap.seguridad.auth;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginRequest;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginResponse;
import com.salesianostriana.dam.campusswap.seguridad.jwt.JwtAccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final RepositorioUsuario repositorioUsuario;

    public LoginResponse doLogin(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password())
        );

        // Rescatar al usuario por username para obtener su id
        Usuario usuario = repositorioUsuario.findByUsername(loginRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.username()));


        String token = jwtAccessTokenService.generateAccessToken(usuario);

        return new LoginResponse(loginRequest.username(), token);

    }


}
