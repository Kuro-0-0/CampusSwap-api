package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;

import java.util.Set;

public record ListarChatResponseDto(
        Long idAnuncio,
        Set<String> participantes,
        MensajeResponseDto ultimoMensaje
) {
    public static ListarChatResponseDto lastMensaje(ListarChatResponseDto chat, Mensaje ultimoMensaje) {
        return new ListarChatResponseDto(
                chat.idAnuncio(),
                chat.participantes(),
                MensajeResponseDto.of(ultimoMensaje)
        );
    }
};
