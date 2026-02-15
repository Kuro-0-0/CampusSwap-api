package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.repositorios.RepositorioFavorito;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioFavorito {

    private final RepositorioFavorito repositorioFavorito;

}
