package com.salesianostriana.dam.campusswap.entidades.extras.dtos;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.validacion.PrecioSegunTipoOperacion;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@PrecioSegunTipoOperacion
public record AnuncioRequestDto(
        @NotBlank String titulo,
        @NotBlank String descripcion,
        @Positive @Nullable Double precio,
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
