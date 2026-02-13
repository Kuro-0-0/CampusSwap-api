package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final RepositorioAnuncio repositorio;


    public Anuncio editarAnuncio(Long id, Anuncio anuncio) {
        /*
        No se pueden editar anuncios cerrados
        Solo el propietario puede modificar su anuncio
        */
        return repositorio.findById(id).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con id: " + id));
    }
}
