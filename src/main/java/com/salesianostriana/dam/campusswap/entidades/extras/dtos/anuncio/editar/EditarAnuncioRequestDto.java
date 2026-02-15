package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.editar;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.PrecioSegunTipoOperacion;
import jakarta.validation.constraints.*;

@PrecioSegunTipoOperacion
public record EditarAnuncioRequestDto(
    @NotBlank @Size(min = 3, max = 100) String titulo,
    @NotBlank @Size(max = 500) String descripcion,
    @Positive Double precio,
    String imagen,
    @NotNull TipoOperacion tipoOperacion,
    @NotNull Condicion condicion,
    @NotNull Long categoriaId,
    @NotNull String usuarioId
) {

    public Anuncio toAnuncio() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .imagen(imagen)
                .categoria(Categoria.builder().id(categoriaId).build())
                .tipoOperacion(tipoOperacion)
                .condicion(condicion)
                .build();
    }
}