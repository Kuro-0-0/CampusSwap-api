package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Favorito;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioFavorito;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioFavorito {

    private final RepositorioFavorito repositorioFavorito;

    public void eliminarFavorito(Long id, String idUsuario) {
        Favorito favorito = repositorioFavorito.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado el favorito con id: " + id));

        if (!favorito.getUsuario().getId().toString().equals(idUsuario))
            throw new NotOwnedException("No puedes eliminar un favorito que no es tuyo");

        repositorioFavorito.delete(favorito);
    }
}
