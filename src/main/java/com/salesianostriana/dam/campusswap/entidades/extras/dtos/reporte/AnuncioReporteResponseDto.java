package com.salesianostriana.dam.campusswap.entidades.extras.dtos.reporte;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;

public record AnuncioReporteResponseDto(
        Long id,
        String titulo,
        String autor
) {

        public static AnuncioReporteResponseDto from(Anuncio anuncio) {
            return new AnuncioReporteResponseDto(anuncio.getId(), anuncio.getTitulo(), anuncio.getUsuario().getNombre());
        }

}
