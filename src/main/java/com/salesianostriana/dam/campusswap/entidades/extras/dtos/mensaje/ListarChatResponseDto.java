package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje;

import java.util.Set;

public record ListarChatResponseDto(
        Long idAnuncio,
        Set<String> participantes
) {};
