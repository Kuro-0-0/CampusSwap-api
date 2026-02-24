package com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaAnuncio;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaUsuario;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ValoracionRequestDto(
    @Max(value = 5, message = "{valoracion.puntuacion.max}")
    @Min(value = 1, message = "{valoracion.puntuacion.min}")
    @NotNull(message = "{valoracion.puntuacion.notnull}")
    Double puntuacion,
    @NotNull(message = "{valoracion.comentario.notnull}")
    @Size(min = 10,max = 500,message = "{valoracion.comentario.size}")
    String comentario,
    @NotNull(message = "{valoracion.anuncioid.notnull}")
    @CheckExistenciaAnuncio
    Long idAnuncio

) {
    public Valoracion to() {
        return Valoracion.builder()
                .puntuacion(puntuacion)
                .comentario(comentario)
                .anuncio(Anuncio.builder().id(idAnuncio).build())
                .build();
    }
}