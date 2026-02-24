package com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria;

import com.salesianostriana.dam.campusswap.entidades.Categoria;
import jakarta.validation.constraints.NotEmpty;

public record CategoriaRequestUpdateDto(
        @NotEmpty(message = "{categoria.nombre.notempty}")
        String nombre,
        @NotEmpty(message = "{categoria.descripcion.notempty}")
        String descripcion
) {

    public Categoria toCategoria() {
        return Categoria.builder()
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .build();
    }

}
