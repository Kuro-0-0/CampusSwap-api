package com.salesianostriana.dam.campusswap.entidades.extras.dtos.reporte;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Reporte;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Motivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReporteRequestDto(
        @NotNull(message = "{reporte.motivo.notnull}")
        Motivo motivo

) {
    public static Reporte toEntity(ReporteRequestDto dto) {
         return Reporte.builder()
                 .motivo(dto.motivo()).build();
    }
}
