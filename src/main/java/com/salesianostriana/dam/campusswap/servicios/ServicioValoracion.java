package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioValoracion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioValoracion {


    private final RepositorioValoracion repositorioValoracion;
    private final RepositorioUsuario  repositorioUsuario;



    public Double calcularMediaValoraciones(String usuarioId) {

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId))
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con ID: " + usuarioId));

        Double media = repositorioValoracion.calcularMediaValoracionesUsuario(usuario.getId());

        if(media == null)
            throw  new  IllegalStateException("El usuario no tiene valoraciones");

        usuario.setReputacionMedia(media);
        repositorioUsuario.save(usuario);
        return media;

    }

    public Page<Valoracion> obtenerValoraciones(Pageable pageable, String usuarioId) {

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId))
                .orElseThrow(()-> new NoSuchElementException("No se ha encontrado el usuario con id: "+usuarioId));

        return repositorioValoracion.findByEvaluadorId(UUID.fromString(usuarioId),pageable);
    }
}
