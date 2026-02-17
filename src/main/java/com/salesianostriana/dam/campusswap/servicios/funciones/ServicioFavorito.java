package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Favorito;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseFavorito;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioFavorito {

    private final ServicioBaseUsuario servicioBaseUsuario;
    private final ServicioBaseAnuncio servicioBaseAnuncio;
    private final ServicioBaseFavorito servicioBaseFavorito;


    public Favorito crearFavorito(Long anuncioId, String usuarioId) {

        Usuario usuario = servicioBaseUsuario.buscarPorId(usuarioId);

        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(anuncioId);

        if (anuncio.getUsuario().equals(usuario))
            throw new IllegalArgumentException("No puedes marcar como favorito un anuncio que tú mismo has creado");

        if (servicioBaseFavorito.existePorUsuarioIdYAnuncioId(UUID.fromString(usuarioId), anuncioId))
            throw new IllegalArgumentException("Ya has marcado este anuncio como favorito");

        Favorito favorito = Favorito.builder()
                .usuario(usuario)
                .anuncio(anuncio)
                .build();

        return servicioBaseFavorito.guardar(favorito);
    }

    public void eliminarFavorito(Long id, String idUsuario) {
        Favorito favorito = servicioBaseFavorito.buscarPorId(id);
        if (!favorito.getUsuario().getId().equals(UUID.fromString(idUsuario)))
            throw new NotOwnedException("No puedes eliminar un favorito que no es tuyo");

        servicioBaseFavorito.borrar(favorito);
    }

    public Page<Favorito> listarFavoritos(Pageable pageable) {
        return servicioBaseFavorito.buscarTodos(pageable);
    }
}
