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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final RepositorioUsuario userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse doLogin(LoginRequest loginRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(), loginRequest.password())
            );
        } catch (LockedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Tu cuenta ha sido bloqueada por un administrador.");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas.");
        }

        Usuario user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + loginRequest.email()));

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
