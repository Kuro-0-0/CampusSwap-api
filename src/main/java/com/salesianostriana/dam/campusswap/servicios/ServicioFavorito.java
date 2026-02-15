package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Favorito;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioFavorito;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioFavorito {

    private final RepositorioFavorito repositorioFavorito;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAnuncio repositorioAnuncio;


    public Favorito crearFavorito(Long anuncioId, String usuarioId) {

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId))
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con id: " + usuarioId));

        Anuncio anuncio = repositorioAnuncio.findById(anuncioId)
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con id: " + anuncioId));

        if (anuncio.getUsuario().equals(usuario))
            throw new IllegalArgumentException("No puedes marcar como favorito un anuncio que tú mismo has creado");

        if (repositorioFavorito.existsByUsuarioIdAndAnuncioId(UUID.fromString(usuarioId), anuncioId))
            throw new IllegalArgumentException("Ya has marcado este anuncio como favorito");

        Favorito favorito = Favorito.builder()
                .usuario(usuario)
                .anuncio(anuncio)
                .build();

        return repositorioFavorito.save(favorito);
    }

    public void eliminarFavorito(Long id, String idUsuario) {
        Favorito favorito = repositorioFavorito.findById(id).orElseThrow(() -> new NoSuchElementException("No se ha encontrado el favorito con id: " + id));
        if (!favorito.getUsuario().getId().toString().equals(idUsuario))
            throw new NotOwnedException("No puedes eliminar un favorito que no es tuyo");

        repositorioFavorito.delete(favorito);
    }
}
