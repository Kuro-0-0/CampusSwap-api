package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.crear;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;

import java.util.UUID;

public record CrearAnuncioRequestDto(
        String titulo,
        String descripcion,
        double precio,
        String imagen,
        TipoOperacion tipoOperacion,
        Condicion condicion,
        String usuarioId,
        Long categoriaId
) {

    public Anuncio toAnuncio() {
        return Anuncio.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .precio(precio)
                .imagen(imagen)
                .tipoOperacion(tipoOperacion)
                .condicion(condicion)
                .usuario(Usuario.builder().id(UUID.fromString(usuarioId)).build())
                .build();
    }
}
