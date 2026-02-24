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
        @NotBlank(message = "{anuncio.titulo.notblank}")
        @Size(min = 3, max = 100, message = "{anuncio.titulo.size}")
        String titulo,
        @NotBlank(message = "{anuncio.descripcion.notblank}")
        @Size(min = 10, max = 500, message = "{anuncio.descripcion.size}")
        String descripcion,
        @Positive(message = "{anuncio.precio.positive}")
        Double precio,
        @NotNull(message = "{anuncio.tipoOperacion.notnull}")
        TipoOperacion tipoOperacion,
        @NotNull(message = "{anuncio.condicion.notnull}")
        Condicion condicion,
        @NotNull(message = "{anuncio.categoria.notnull}")
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
