package com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria;

import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.UniqueNombreCategoria;
import jakarta.validation.constraints.NotEmpty;

public record CategoriaRequestDto(
        @NotEmpty(message = "{categoria.nombre.notempty}")
        @UniqueNombreCategoria(message = "El nombre de la categoría ya existe")
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
