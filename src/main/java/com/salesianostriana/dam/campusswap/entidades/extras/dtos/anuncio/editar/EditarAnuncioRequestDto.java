package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.editar;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record EditarAnuncioRequestDto(
    @NotBlank String titulo,
    @NotBlank String descripcion,
    @Nullable  Double precio,
    String imagen,
    @NotBlank TipoOperacion tipoOperacion,
    @NotBlank Condicion condicion,
    @NotEmpty Long categoriaId,
    @NotBlank String usuarioId
) {

    public Anuncio toAnuncio() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .imagen(imagen)
                .tipoOperacion(tipoOperacion)
                .condicion(condicion)
                .build();
    }
}