package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Log
@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final RepositorioAnuncio repositorioAnuncio;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCategoria repositorioCategoria;

    public Anuncio crearAnuncio(Anuncio anuncio) {
        Usuario usuario = repositorioUsuario.findById(anuncio.getUsuario().getId()).orElseThrow(() -> new NoSuchElementException("Usuario con ID " + anuncio.getUsuario().getId() + " no encontrado"));
        Categoria categoria = repositorioCategoria.findById(anuncio.getCategoria().getId()).orElseThrow(() -> new NoSuchElementException("Categoría con ID " + anuncio.getCategoria().getId() + " no encontrada"));

        anuncio.setEstado(Estado.ACTIVO);
        usuario.agregarAnuncio(anuncio);
        categoria.addAnuncio(anuncio);
        repositorioCategoria.save(categoria);
        repositorioUsuario.save(usuario);
        return repositorioAnuncio.save(anuncio);
    }

    public Anuncio editarAnuncio(Long id, Anuncio anuncio, String usuarioId) {
        Anuncio original = repositorioAnuncio.findById(id).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con id: " + id));

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId)).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con id: " + usuarioId));

        Categoria categoria = repositorioCategoria.findById(anuncio.getCategoria().getId()).orElseThrow(() -> new NoSuchElementException("No se ha encontrado la categoría con id: " + anuncio.getCategoria().getId()));

        if (original.getUsuario() == null || !original.getUsuario().equals(usuario))
            throw new NotOwnedException("No puedes modificar un anuncio que no es tuyo");

        if (original.getEstado().equals(Estado.CERRADO))
            throw new IllegalStateException("No se pueden modificar anuncios cerrados");

        categoria.addAnuncio(original);
        repositorioCategoria.save(categoria);

        return repositorioAnuncio.save(original.modificar(anuncio));
    }


    public Anuncio alternarEstado(Long id, String usuarioId) {
        Anuncio anuncio = repositorioAnuncio.findById(id).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con id: " + id));
        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId)).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con id: " + usuarioId));

        if (anuncio.getUsuario() == null || !anuncio.getUsuario().equals(usuario))
            throw new NotOwnedException("No puedes modificar un anuncio que no es tuyo");

        if (anuncio.getEstado().equals(Estado.CERRADO))
            anuncio.setEstado(Estado.ACTIVO);
        else if (anuncio.getEstado().equals(Estado.ACTIVO))
            anuncio.setEstado(Estado.CERRADO);
        else
            throw new IllegalStateException("No se pueden modificar anuncios con estado: " + anuncio.getEstado());

        return repositorioAnuncio.save(anuncio);
    }
}
