package com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaAnuncio;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaUsuario;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ValoracionRequestDto(
    @Max(value = 5, message = "La puntuación no puede ser mayor que 5")
    @Min(value = 1, message = "La puntuación no puede ser menor que 1")
    double puntuacion,
    String comentario,
    @NotNull @CheckExistenciaAnuncio
    Long idAnuncio,
    @NotNull @CheckExistenciaUsuario
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