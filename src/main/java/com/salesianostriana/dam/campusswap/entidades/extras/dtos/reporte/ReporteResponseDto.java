package com.salesianostriana.dam.campusswap.entidades.extras.dtos.reporte;

import com.salesianostriana.dam.campusswap.entidades.Reporte;

public record ReporteResponseDto(Long id, String motivo, Long anuncioId, String usuarioId) {

    public static ReporteResponseDto from(Reporte reporte) {
        return new ReporteResponseDto(
                reporte.getId(),
                reporte.getMotivo().getDescripcion(),
                reporte.getAnuncio().getId(),
                reporte.getUsuario().getId().toString()
        );
    }
}
