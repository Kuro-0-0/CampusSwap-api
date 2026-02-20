package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.chat;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.MensajeResponseDto;

import java.util.Set;

public record ListarChatResponseDto(
        AnuncioChatDto anuncio,
        Set<UsuarioChatDto> participantes,
        MensajeResponseDto ultimoMensaje
) {
    public static ListarChatResponseDto lastMensaje(ListarChatResponseDto chat, Mensaje ultimoMensaje) {
        return new ListarChatResponseDto(
                chat.anuncio(),
                chat.participantes(),
                MensajeResponseDto.of(ultimoMensaje)
        );
    }
};
