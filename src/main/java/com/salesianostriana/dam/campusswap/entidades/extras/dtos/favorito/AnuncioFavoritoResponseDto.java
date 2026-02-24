package com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;

public record AnuncioFavoritoResponseDto(
        Long id,
        String titulo,
        String imagen
) {

    public static AnuncioFavoritoResponseDto of(Anuncio anuncio) {
        return new AnuncioFavoritoResponseDto(anuncio.getId(), anuncio.getTitulo(), anuncio.getImagen());
    }

}
