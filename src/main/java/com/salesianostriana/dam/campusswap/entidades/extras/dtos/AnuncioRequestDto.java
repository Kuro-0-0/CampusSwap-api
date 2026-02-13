package com.salesianostriana.dam.campusswap.entidades.extras.dtos;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;

public record AnuncioRequestDto(
        String titulo,
        String descripcion,
        double precio,
        Long categoriaId,
        String imagen,
        TipoOperacion tipoOperacion,
        Estado estado,
        Condicion condicion,
        String usuarioId,
) {

    public Anuncio toAnuncio() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .tipoOperacion(tipoOperacion)
                .estado(estado)
                .condicion(condicion)
                .usuario(Usuario.builder().id(usuarioId).build())
                .build();
    }
}
