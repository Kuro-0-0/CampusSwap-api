package com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.campusswap.entidades.Favorito;

import java.time.LocalDateTime;

public record FavoritoResponseDto(
        String nombreUsuario,
        String tituloAnuncio,
        Double precio,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fechaFavorito
) {
    public static FavoritoResponseDto of(Favorito favorito) {
        return new FavoritoResponseDto(
                favorito.getUsuario().getNombre(),
                favorito.getAnuncio().getTitulo(),
                favorito.getAnuncio().getPrecio(),
                favorito.getFecha()
        );
    }
}
