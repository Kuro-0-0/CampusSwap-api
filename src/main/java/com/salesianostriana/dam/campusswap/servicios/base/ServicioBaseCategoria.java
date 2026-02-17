package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ServicioBaseCategoria {

    private final RepositorioCategoria repositorioCategoria;

    public Categoria buscarPorId(Long id) {
        return repositorioCategoria.findById(id)
                .orElseThrow(()
                        -> new NoSuchElementException("Categoría con ID " + id + " no encontrada")
                );
    }


}
