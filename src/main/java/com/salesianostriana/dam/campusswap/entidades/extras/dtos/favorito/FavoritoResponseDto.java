package com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito;

import com.salesianostriana.dam.campusswap.entidades.Favorito;

import java.time.LocalDateTime;

public record FavoritoResponseDto(
        String nombreUsuario,
        String tituloAnuncio,
        LocalDateTime fechaFavorito
) {
    public static FavoritoResponseDto of(Favorito favorito) {
        return new FavoritoResponseDto(
                favorito.getUsuario().getNombre(),
                favorito.getAnuncio().getTitulo(),
                favorito.getFecha()
        );
    }
}
