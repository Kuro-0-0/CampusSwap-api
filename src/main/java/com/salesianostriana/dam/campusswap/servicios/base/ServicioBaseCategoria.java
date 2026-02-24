package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
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


    public Categoria guardar(Categoria categoria) {
        return repositorioCategoria.save(categoria);
    }

    public boolean existePorNombre(String nombre) {
        return repositorioCategoria.existsByNombre(nombre);
    }

    public void borrar(Categoria categoria) {
        repositorioCategoria.delete(categoria);
    }

    public Page<Categoria> buscarTodos(PredicateSpecification<Categoria> spec, Pageable pageable) {
        return repositorioCategoria.findBy(spec, p -> p.page(pageable));
    }
}
