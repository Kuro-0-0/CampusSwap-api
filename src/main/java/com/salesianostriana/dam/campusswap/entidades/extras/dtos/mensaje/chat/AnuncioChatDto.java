package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.chat;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;

public record AnuncioChatDto(
        Long id,
        String titulo,
        String imagen
) {

    public static AnuncioChatDto of(Anuncio a) {
        return new AnuncioChatDto(a.getId(), a.getTitulo(), a.getImagen());
    }

}
