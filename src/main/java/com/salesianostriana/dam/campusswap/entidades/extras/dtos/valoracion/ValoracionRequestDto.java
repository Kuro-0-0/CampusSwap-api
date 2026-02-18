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
    @Max(value = 5, message = "La puntuación no puede ser mayor que 5")
    @Min(value = 1, message = "La puntuación no puede ser menor que 1")
    @NotNull(message = "La puntuación no puede ser nula")
    Double puntuacion,
    @NotNull(message = "El comentario no puede estar vacío")
    @Size(min = 10,max = 500,message = "El comentario debe tener entre 10 y 500 caracteres")
    String comentario,
    @NotNull @CheckExistenciaAnuncio
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