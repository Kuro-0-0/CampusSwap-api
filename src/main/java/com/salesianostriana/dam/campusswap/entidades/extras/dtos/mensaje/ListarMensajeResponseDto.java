package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;

import java.time.LocalDateTime;

public record ListarMensajeResponseDto(
        String idEmisor,
        String nombreEmisor,
        String fotoEmisor,
        String mensaje,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fechaMensaje
) {

        public static ListarMensajeResponseDto of(Mensaje mensaje) {
            return new ListarMensajeResponseDto(
                    mensaje.getEmisor().getId().toString(),
                    mensaje.getEmisor().getNombre(),
                    mensaje.getEmisor().getFotoPerfil(),
                    mensaje.getContenido(),
                    mensaje.getFechaEnvio());
        }

}
