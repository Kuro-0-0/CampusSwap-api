package com.salesianostriana.dam.campusswap.seguridad.auth.dto;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.ContrasenaNoCoincide;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.EmailUnico;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.UsernameUnico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@ContrasenaNoCoincide
public record RegisterRequest(
        @NotBlank(message = "{registro.nombre.notblank}")
        @Size(min = 2, max = 100, message = "{registro.nombre.size}")
        String nombre,
        @NotBlank(message = "{registro.username.notblank}")
        @Size(min = 3, max = 50, message = "{registro.username.size}")
        @UsernameUnico
        String username,
        @NotBlank(message = "{registro.password.notblank}")
        @Size(min = 8, max = 255, message = "{registro.password.size}")
        String password,
        @NotBlank(message = "{registro.repeatpassword.notblank}")
        String repeatPassword,
        @NotBlank(message = "{registro.email.notblank}")
        @Email(message = "{registro.email.valid}")
        @EmailUnico
        String email
) {
    public Usuario toUsuario() {
        return Usuario.builder()
                .nombre(nombre)
                .username(username)
                .contrasena(password)
                .email(email)
                .build();
    }
}
