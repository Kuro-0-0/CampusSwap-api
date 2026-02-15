package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;

public record AnuncioResponseDto(
        Long id,
        String titulo,
        String descripcion,
        Double precio,
        String categoria,
        String imagen,
        String tipoOperacion,
        String estado,
        String condicion,
        String usuarioId
) {
        public static AnuncioResponseDto of(Anuncio anuncio) {
            return new AnuncioResponseDto(
                    anuncio.getId(),
                    anuncio.getTitulo(),
                    anuncio.getDescripcion(),
                    anuncio.getPrecio(),
                    anuncio.getCategoria().getNombre(),
                    anuncio.getImagen(),
                    anuncio.getTipoOperacion().name(),
                    anuncio.getEstado().name(),
                    anuncio.getCondicion().name(),
                    anuncio.getUsuario().getId().toString()
            );
        }
}
