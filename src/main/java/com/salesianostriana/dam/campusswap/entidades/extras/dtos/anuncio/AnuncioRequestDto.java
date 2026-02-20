package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.PrecioSegunTipoOperacion;
import jakarta.validation.constraints.*;

import java.util.UUID;

@PrecioSegunTipoOperacion
public record AnuncioRequestDto(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
        String titulo,
        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 1000 caracteres")
        String descripcion,
        @Positive(message = "El precio debe ser mayor que 0 para venta")
        Double precio,
        @NotNull(message = "El tipo de operación no puede ser nulo")
        TipoOperacion tipoOperacion,
        @NotNull(message = "La condición no puede ser nula")
        Condicion condicion,
        @NotNull(message = "La categoría no puede ser nula")
        Long categoriaId
) {

    public Anuncio toAnuncio() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .tipoOperacion(tipoOperacion)
                .condicion(condicion)
                .categoria(Categoria.builder().id(categoriaId).build())
                .build();
    }
}
