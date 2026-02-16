package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioValoracion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValoracionServicio {


    private final RepositorioValoracion repositorioValoracion;
    private final RepositorioUsuario  repositorioUsuario;



    public Double calcularMediaValoraciones(String usuarioId) {

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId))
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        Double media = repositorioValoracion.calcularMediaValoracionesUsuario(usuario.getId());

        usuario.setReputacionMedia(media);
        repositorioUsuario.save(usuario);
        return media;

    }
}
