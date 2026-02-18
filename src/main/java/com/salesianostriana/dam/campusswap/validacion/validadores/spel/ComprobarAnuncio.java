package com.salesianostriana.dam.campusswap.validacion.validadores.spel;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("comprobarAnuncio")
public class ComprobarAnuncio {

    @Autowired
    private ServicioBaseAnuncio servicioBaseAnuncio;

    public boolean esPropietario(Long id, Usuario usuario) {
        return servicioBaseAnuncio.buscarPorId(id).getUsuario().equals(usuario);
    }

}
