package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio;

import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;

public record AnuncioFiltroDto (
        String q,
        Long categoriaId,
        Double minPrecio,
        Double maxPrecio,
        TipoOperacion tipoOperacion,
        Estado estado
){
    public static AnuncioFiltroDto of(String q, Long categoriaId, Double minPrecio, Double maxPrecio, TipoOperacion tipoOperacion, Estado estado) {
        return new AnuncioFiltroDto(q, categoriaId, minPrecio, maxPrecio, tipoOperacion, estado);
    }
}
