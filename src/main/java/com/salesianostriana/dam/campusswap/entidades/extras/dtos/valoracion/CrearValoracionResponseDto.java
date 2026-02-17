package com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;

import java.time.LocalDateTime;

public record CrearValoracionResponseDto (
        double puntuacion,
        String comentario,
        String nombreEvaluador,
        String nombreEvaluado,
        String tituloAnuncio,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
        LocalDateTime fecha

) {

    public static CrearValoracionResponseDto of(Valoracion v) {
        return new CrearValoracionResponseDto(
                v.getPuntuacion(),
                v.getComentario(),
                v.getEvaluador().getNombre(),
                v.getEvaluado().getNombre(),
                v.getAnuncio().getTitulo(),
                v.getFecha()
        );
    }

}