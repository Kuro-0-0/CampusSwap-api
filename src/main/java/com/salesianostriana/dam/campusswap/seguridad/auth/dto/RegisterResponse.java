package com.salesianostriana.dam.campusswap.seguridad.auth.dto;

import com.salesianostriana.dam.campusswap.entidades.Usuario;

public record RegisterResponse(
        String uuid,
        String nombre,
        String username,
        String email,
        String token

) {

    public static RegisterResponse fromUsuario(Usuario usuario, String token) {
        return new RegisterResponse(
                usuario.getId().toString(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getEmail(),
                token
        );
    }
}
