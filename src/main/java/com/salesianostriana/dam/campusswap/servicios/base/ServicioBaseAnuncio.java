package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioBaseAnuncio {

    private final RepositorioAnuncio repositorioAnuncio;

    public Anuncio buscarPorId(Long id) {
        return repositorioAnuncio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Anuncio con ID " + id + " no encontrado"));
    }


    public Anuncio guardar(Anuncio anuncio) {
        return repositorioAnuncio.save(anuncio);
    }

    public Page<Anuncio> findByUsuarioId(String id, Pageable pageable) {
        return findByUsuarioId(UUID.fromString(id), pageable);
    }

    public Page<Anuncio> findByUsuarioId(UUID uuid, Pageable pageable) {
        return repositorioAnuncio.findByUsuarioId(uuid, pageable);
    }

    public void borrar(Anuncio anuncio) {
        repositorioAnuncio.delete(anuncio);
    }

    public Page<Anuncio> buscarFiltradoPageado(PredicateSpecification<Anuncio> pred, Pageable pageable) {
        return repositorioAnuncio.findBy(pred, p -> p.page(pageable));
    }

    public boolean existePorId(Long idAnuncio) {
        return repositorioAnuncio.existsById(idAnuncio);
    }
}
