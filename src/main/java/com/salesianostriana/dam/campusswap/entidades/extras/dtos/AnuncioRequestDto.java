package com.salesianostriana.dam.campusswap.entidades.extras.dtos;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record AnuncioRequestDto(
        @NotBlank String titulo,
        @NotBlank String descripcion,
        @Nullable Double precio,
        @NotBlank String imagen,
        @NotBlank TipoOperacion tipoOperacion,
        @NotBlank Condicion condicion,
        @NotBlank String usuarioId,
        @NotEmpty Long categoriaId
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
                .build();
    }
}
