package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.editar;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;

public record EditarAnuncioRequestDto(
    String titulo,
    String descripcion,
    double precio,
    String imagen,
    TipoOperacion tipoOperacion,
    Condicion condicion,
    Long categoriaId,
    String usuarioId
) {

    public Anuncio toAnuncio() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .imagen(imagen)
                .tipoOperacion(tipoOperacion)
                .condicion(condicion)
                .build();
    }
}