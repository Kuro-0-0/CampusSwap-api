package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje;

import java.time.LocalDateTime;

public record MensajeFiltroDto(
        String titulo,
        String nombre,
        LocalDateTime desde,
        LocalDateTime hasta
) {
}
