package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio;

public record EditarAnuncioResponseDTO(
    Long id,
    String titulo,
    String descripcion,
    Double precio,
    String tipoOperacion,
    String estado,
    String condicion,
    String image
) {
}
