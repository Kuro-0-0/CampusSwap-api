package com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaAnuncio;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaUsuario;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record ValoracionRequestDto(
    @Max(5) @Min(1)
    double puntuacion,
    String comentario,
    @CheckExistenciaAnuncio
    Long idAnuncio,
    @CheckExistenciaUsuario
    String idEvaluador
) {
    public Valoracion to() {
        return Valoracion.builder()
                .puntuacion(puntuacion)
                .comentario(comentario)
                .anuncio(Anuncio.builder().id(idAnuncio).build())
                .evaluador(Usuario.builder().id(UUID.fromString(idEvaluador)).build())
                .build();
    }
}