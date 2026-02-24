package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnviarMensajeRequestDto(
        @NotBlank(message = "{mensaje.contenido.notblank}")
        String contenido,
        @NotNull(message = "{mensaje.anuncioId.notnull}")
        Long anuncioId,
        @NotBlank(message = "{mensaje.receptorId.notblank}")
        String receptorId) {

     public static Mensaje from(EnviarMensajeRequestDto dto) {
         return Mensaje.builder()
                 .contenido(dto.contenido())
                 .anuncio(Anuncio.builder().id(dto.anuncioId()).build())
                 .receptor(Usuario.builder().id(UUID.fromString(dto.receptorId())).build())
                 .build();
     }
}
