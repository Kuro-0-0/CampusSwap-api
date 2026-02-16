package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioMensaje;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@CommonsLog
@Service
@RequiredArgsConstructor
public class ServicioMensaje {

    private final RepositorioMensaje repositorioMensaje;
    private final RepositorioAnuncio repositorioAnuncio;


    public Page<Mensaje> obtenerMensajes(Long idAnuncio, Pageable pageable) {
        return repositorioMensaje.findAllByAnuncioId(idAnuncio, pageable);
        if (!repositorioAnuncio.existsById(idAnuncio))
            throw new NoSuchElementException("No se ha encontrado el anuncio con id: " + idAnuncio);
        return repositorioMensaje.findAllByAnuncioId(pageable,idAnuncio);
    }
}
