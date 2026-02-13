package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;

public record EditarAnuncioRequestDTO(
        String titulo,
        String descripcion,
        Double precio,
        String tipoOperacion,
        String estado,
        String condicion
) {

    public Anuncio of() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .tipoOperacion(TipoOperacion.valueOf(tipoOperacion))
                .estado(Estado.valueOf(estado))
                .condicion(Condicion.valueOf(condicion))
                .build();
    }

}
