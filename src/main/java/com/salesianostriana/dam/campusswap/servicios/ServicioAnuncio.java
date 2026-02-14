package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final RepositorioAnuncio repositorio;

    private final RepositorioUsuario repositorioUsuario;


    public Anuncio editarAnuncio(Long id, Anuncio anuncio, String usuarioId) {
        Anuncio original = repositorio.findById(id).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con id: " + id));

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId)).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con id: " + usuarioId));

        if (original.getUsuario() == null || original.getUsuario() != usuario)
            throw new NotOwnedException("No puedes modificar un anuncio que no es tuyo");

        if (original.getEstado().equals(Estado.CERRADO))
            throw new IllegalStateException("No se pueden modificar anuncios cerrados");

        return original.modificar(anuncio);
    }
}
