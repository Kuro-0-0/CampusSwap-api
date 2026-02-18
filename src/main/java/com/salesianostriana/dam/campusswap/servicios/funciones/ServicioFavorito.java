package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Favorito;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseFavorito;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
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


    public Favorito crearFavorito(Long id, Usuario usuario) {

        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(id);

        if (anuncio.getUsuario().equals(usuario))
            throw new IllegalArgumentException("No puedes marcar como favorito un anuncio que tú mismo has creado");

        if (servicioBaseFavorito.existePorUsuarioIdYAnuncioId(usuario.getId(), id))
            throw new IllegalArgumentException("Ya has marcado este anuncio como favorito");


        return servicioBaseFavorito.guardar(Favorito.builder()
                .usuario(usuario)
                .anuncio(anuncio)
                .build());
    }

    public void eliminarFavorito(Long id, Usuario usuario) {
        Favorito favorito = servicioBaseFavorito.buscarPorId(id);
        if (!favorito.getUsuario().equals(usuario))
            throw new NotOwnedException("No puedes eliminar un favorito que no es tuyo");

        servicioBaseFavorito.borrar(favorito);
    }

    public Page<Favorito> listarFavoritos(Pageable pageable) {
        return servicioBaseFavorito.buscarTodos(pageable);
    }
}
