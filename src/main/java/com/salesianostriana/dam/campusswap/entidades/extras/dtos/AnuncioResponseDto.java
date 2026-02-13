package com.salesianostriana.dam.campusswap.entidades.extras.dtos;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;

public record AnuncioResponseDto(
        Long id,
        String titulo,
        String descripcion,
        double precio,
        String categoria,
        String imagen,
        String tipoOperacion,
        String estado,
        String condicion,
        String usuarioId,
        Long categoriaId
) {
        public static AnuncioResponseDto of(Anuncio anuncio) {
            return new AnuncioResponseDto(
                    anuncio.getId(),
                    anuncio.getTitulo(),
                    anuncio.getDescripcion(),
                    anuncio.getPrecio(),
                    anuncio.getUsuario().getNombre(),
                    null, // Aquí deberías mapear la imagen si la tienes en tu entidad Anuncio
                    anuncio.getTipoOperacion().name(),
                    anuncio.getEstado().name(),
                    anuncio.getCondicion().name(),
                    anuncio.getUsuario().getId().toString(),
                    null // Aquí deberías mapear el ID de la categoría si la tienes en tu entidad Anuncio
            );
        }
}
