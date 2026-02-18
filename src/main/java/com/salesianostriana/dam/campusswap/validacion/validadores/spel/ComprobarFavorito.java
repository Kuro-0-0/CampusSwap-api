package com.salesianostriana.dam.campusswap.validacion.validadores.spel;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseFavorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("comprobarFavorito")
public class ComprobarFavorito {

    @Autowired
    private ServicioBaseFavorito servicioBaseFavorito;

    public boolean esPropietario(Long id, Usuario usuario) {
        return servicioBaseFavorito.buscarPorId(id).getUsuario().equals(usuario);
    }

}
