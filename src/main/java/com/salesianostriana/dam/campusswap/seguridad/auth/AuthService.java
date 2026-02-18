package com.salesianostriana.dam.campusswap.seguridad.auth;


import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.RolUsuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginRequest;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginResponse;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.RegisterRequest;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.RegisterResponse;
import com.salesianostriana.dam.campusswap.seguridad.jwt.JwtAccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final RepositorioUsuario userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse doLogin(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(), loginRequest.password())
        );

        Usuario user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.email()));


        String token = jwtAccessTokenService.generateAccessToken(user);

        return new LoginResponse(loginRequest.email(), token);

    }

    public RegisterResponse doRegister(RegisterRequest registerRequest){

        Usuario nuevoUsuario = registerRequest.toUsuario();
        nuevoUsuario.setContrasena(passwordEncoder.encode(nuevoUsuario.getContrasena()));
        nuevoUsuario.addRol(RolUsuario.USUARIO);

        userRepository.save(nuevoUsuario);
        String token = jwtAccessTokenService.generateAccessToken(nuevoUsuario);

        return RegisterResponse.fromUsuario(nuevoUsuario, token);
    }


}
