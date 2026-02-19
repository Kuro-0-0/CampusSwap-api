package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaResponseDto;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioCategoria {

    private final RepositorioCategoria repositorioCategoria;

    public List<Categoria> obtenerTodas(){
        return repositorioCategoria.findAll();
    }
}
