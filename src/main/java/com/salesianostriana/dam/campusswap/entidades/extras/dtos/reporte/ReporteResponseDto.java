package com.salesianostriana.dam.campusswap.entidades.extras.dtos.reporte;

import com.salesianostriana.dam.campusswap.entidades.Reporte;

public record ReporteResponseDto(
        Long id,
        String motivo,
        AnuncioReporteResponseDto anuncio,
        Long cantidad
) {

    public static ReporteResponseDto from(Reporte reporte, Long conteo) {
        return new ReporteResponseDto(
                reporte.getId(),
                reporte.getMotivo().getDescripcion(),
                AnuncioReporteResponseDto.from(reporte.getAnuncio()),
                conteo
        );
    }
}
