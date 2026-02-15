package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final RepositorioAnuncio repositorioAnuncio;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCategoria repositorioCategoria;

    public Anuncio crearAnuncio(Anuncio anuncio) {
        Usuario usuario = repositorioUsuario.findById(anuncio.getUsuario().getId()).orElseThrow(() -> new NoSuchElementException("Usuario con ID " + anuncio.getUsuario().getId() + " no encontrado"));

        if((anuncio.getTipoOperacion().equals(TipoOperacion.CESION) || anuncio.getTipoOperacion().equals(TipoOperacion.INTERCAMBIO)) && anuncio.getPrecio() != null) {
            throw new IllegalArgumentException("Un anuncio de cesión o intercambio no puede tener un precio establecido");
        }

        anuncio.setUsuario(usuario);
        anuncio.setEstado(Estado.ACTIVO);
        usuario.agregarAnuncio(anuncio);
        return repositorioAnuncio.save(anuncio);
    }

    public Anuncio editarAnuncio(Long id, Anuncio anuncio, String usuarioId) {
        if((anuncio.getTipoOperacion().equals(TipoOperacion.CESION) || anuncio.getTipoOperacion().equals(TipoOperacion.INTERCAMBIO)) && anuncio.getPrecio() != null) {
            throw new IllegalArgumentException("Un anuncio de cesión o intercambio no puede tener un precio establecido");
        }

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


}
