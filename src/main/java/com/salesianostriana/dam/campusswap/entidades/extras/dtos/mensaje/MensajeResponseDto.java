package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;

public record MensajeResponseDto(
        Long id,
        String contenido,
        String fechaEnvio,
        Long anuncioId,
        String emisorId,
        String receptorId
) {

    public static MensajeResponseDto of(Mensaje mensaje) {
        return new MensajeResponseDto(
                mensaje.getId(),
                mensaje.getContenido(),
                mensaje.getFechaEnvio().toString(),
                mensaje.getAnuncio().getId(),
                mensaje.getEmisor().getId().toString(),
                mensaje.getReceptor().getId().toString()
        );
    }
}
