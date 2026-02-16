package com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;

import java.time.LocalDateTime;

public record ValoracionResponseDto(
        Long id,
        Double puntuacion,
        String comentario,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fecha,
        String evaluadoNombre,
        String fotoPerfilEvaluado,
        String anuncioTitulo
) {
    public static ValoracionResponseDto of(Valoracion valoracion){
        return new ValoracionResponseDto(
                valoracion.getId(),
                valoracion.getPuntuacion(),
                valoracion.getComentario(),
                valoracion.getFecha(),
                valoracion.getEvaluado().getNombre(),
                valoracion.getEvaluado().getFotoPerfil(),
                valoracion.getAnuncio().getTitulo()
        );
    }
}
