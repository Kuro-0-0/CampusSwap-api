package com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria;

import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.UniqueNombreCategoria;
import jakarta.validation.constraints.NotEmpty;

public record CategoriaRequestUpdateDto(
        @NotEmpty(message = "El nombre de la categoría no puede estar vacío")
        String nombre,
        @NotEmpty(message = "La descripción de la categoría no puede estar vacía")
        String descripcion
) {

    public Categoria toCategoria() {
        return Categoria.builder()
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .build();
    }

}
