package com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria;

import com.salesianostriana.dam.campusswap.entidades.Categoria;

public record CategoriaResponseDto(
        Long id,
        String nombre,
        String descripcion
) {
    public static CategoriaResponseDto of(Categoria c) {
        return new CategoriaResponseDto(c.getId(), c.getNombre(), c.getDescripcion());
    }
}
