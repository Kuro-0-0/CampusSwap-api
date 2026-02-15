package com.salesianostriana.dam.campusswap.entidades.extras.dtos;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.validacion.PrecioSegunTipoOperacion;
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
        @NotBlank(message = "La imagen no puede estar vacía")
        String imagen,
        @NotNull(message = "El tipo de operación no puede ser nulo")
        TipoOperacion tipoOperacion,
        @NotNull(message = "La condición no puede ser nula")
        Condicion condicion,
        @NotBlank(message = "El ID de usuario no puede estar vacío")
        String usuarioId,
        @NotEmpty(message = "La categoría no puede estar vacía")
        Long categoriaId
) {

    public Anuncio toAnuncio() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .imagen(imagen)
                .tipoOperacion(tipoOperacion)
                .condicion(condicion)
                .usuario(Usuario.builder().id(UUID.fromString(usuarioId)).build())
                .categoria(Categoria.builder().id(categoriaId).build())
                .build();
    }
}
