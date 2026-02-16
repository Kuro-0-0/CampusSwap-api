package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioMensaje;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@CommonsLog
@Service
@RequiredArgsConstructor
public class ServicioMensaje {

    private final RepositorioMensaje repositorioMensaje;


    public Page<Mensaje> obtenerMensajes(Long idAnuncio, Pageable pageable) {
        return repositorioMensaje.findAllByAnuncioId(idAnuncio, pageable);
    }
}
