package com.salesianostriana.dam.campusswap.entidades.extras.dtos.reporte;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Reporte;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Motivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReporteRequestDto(
        @NotNull(message = "El motivo no puede ser nulo")
        Motivo motivo,
        @NotBlank(message = "El ID del usuario no puede estar vacío")
        String usuarioId

) {
    public static Reporte toEntity(ReporteRequestDto dto) {
         return Reporte.builder()
                 .motivo(dto.motivo())
                 .usuario(Usuario.builder().id(UUID.fromString(dto.usuarioId())).build())
                 .build();
    }
}
