package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnviarMensajeRequestDto(
        @NotBlank(message = "El contenido no puede estar vacío")
        String contenido,
        @NotNull(message = "El ID del anuncio no puede ser nulo")
        Long anuncioId,
        @NotBlank(message = "El ID del receptor no puede estar vacío")
        String receptorId) {

     public static Mensaje from(EnviarMensajeRequestDto dto) {
         return Mensaje.builder()
                 .contenido(dto.contenido())
                 .anuncio(Anuncio.builder().id(dto.anuncioId()).build())
                 .receptor(Usuario.builder().id(UUID.fromString(dto.receptorId())).build())
                 .build();
     }
}
