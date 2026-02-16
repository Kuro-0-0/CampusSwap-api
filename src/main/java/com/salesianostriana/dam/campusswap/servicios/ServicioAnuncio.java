package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.Reporte;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioReporte;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final RepositorioReporte repositorioReporte;

    public Anuncio crearAnuncio(Anuncio anuncio) {
        Usuario usuario = repositorioUsuario.findById(anuncio.getUsuario().getId()).orElseThrow(() -> new NoSuchElementException("Usuario con ID " + anuncio.getUsuario().getId() + " no encontrado"));
        Categoria categoria = repositorioCategoria.findById(anuncio.getCategoria().getId()).orElseThrow(() -> new NoSuchElementException("Categoría con ID " + anuncio.getCategoria().getId() + " no encontrada"));

        anuncio.setEstado(Estado.ACTIVO);
        usuario.agregarAnuncio(anuncio);
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

        return repositorioAnuncio.save(original.modificar(anuncio));
    }

    public Page<Anuncio> obtenerAnuncios(Pageable pageable, String idUsuario) {
        Usuario usuario = repositorioUsuario.findById(UUID.fromString(idUsuario)).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con id: " + idUsuario));

        return repositorioAnuncio.findByUsuarioId(UUID.fromString(idUsuario), pageable);
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


    public void borrarAnuncio(Long id, String idUsuario) {

        Anuncio anuncio = repositorioAnuncio.findById(id).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con id: " + id));
        Usuario usuario = repositorioUsuario.findById(UUID.fromString(idUsuario)).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con id: " + idUsuario));


        if(!anuncio.getUsuario().equals(usuario)){
            throw new NotOwnedException("No se puede eliminar un anuncio que no te pertenece");
        }

        usuario.borrarAnuncio(anuncio);
        repositorioUsuario.save(usuario);
        repositorioAnuncio.delete(anuncio);


    }

    public Reporte reportarAnuncio(Long anuncioId, Reporte reporte) {
        Anuncio anuncio = repositorioAnuncio.findById(anuncioId).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con id: " + anuncioId));
        Usuario usuario = repositorioUsuario.findById(reporte.getUsuario().getId()).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con id: " + reporte.getUsuario().getId().toString()));

        if(repositorioReporte.findByAnuncioIdAndUsuarioId(anuncio.getId(), usuario.getId()).isPresent()){
            throw new IllegalStateException("Ya has reportado este anuncio");
        }

        if (anuncio.getEstado().equals(Estado.CERRADO)){
            throw new IllegalStateException("No se pueden reportar anuncios cerrados");
        }

        if (anuncio.getUsuario().equals(usuario)){
            throw new IllegalStateException("No se pueden reportar tus propios anuncios");
        }

        reporte.setAnuncio(anuncio);
        reporte.setUsuario(usuario);
        return repositorioReporte.save(reporte);
    }

}
