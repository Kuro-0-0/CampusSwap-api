package com.salesianostriana.dam.campusswap.entidades.extras.dtos.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.campusswap.entidades.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record UsuarioResponseDto(
        String id,
        String nombre,
        String email,
        double reputacionMedia,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fechaRegistro,
        Set<String> roles

) {

    public static UsuarioResponseDto of(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getId().toString(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getReputacionMedia(),
                usuario.getFechaRegistro(),
                usuario.getRoles().stream().map(r -> r.name()).collect(Collectors.toSet())
        );
    }

}
