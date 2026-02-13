package com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.editar;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckUserExist;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record EditarAnuncioRequestDto(
    String titulo,
    String descripcion,
    double precio,
    String imagen,
    TipoOperacion tipoOperacion,
    Condicion condicion,
    Long categoriaId,
    @CheckUserExist(message = "El usuario con el ID proporcionado no existe")
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