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
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,
        @NotBlank(message = "El username no puede estar vacío")
        @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
        @UsernameUnico
        String username,
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, max = 255, message = "La contraseña debe tener al menos 8 caracteres")
        String password,
        @NotBlank(message = "Debe confirmar la contraseña")
        String repeatPassword,
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email debe ser válido")
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
