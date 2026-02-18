package com.salesianostriana.dam.campusswap.validacion.validadores.spel;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseFavorito;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseMensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("comprobarMensaje")
public class ComprobarMensaje {

    @Autowired
    private ServicioBaseMensaje servicioBaseMensaje;

    public boolean esParticipante(Long id, Usuario usuario) {
        return !servicioBaseMensaje.buscarTodosPorAnuncioIdYUsuarioId(id, usuario.getId()).isEmpty();
    }

}
