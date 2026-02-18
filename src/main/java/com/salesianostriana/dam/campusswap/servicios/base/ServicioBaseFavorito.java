package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Favorito;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioFavorito;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioBaseFavorito
{

    private final RepositorioFavorito repositorioFavorito;

    public boolean existePorUsuarioIdYAnuncioId(UUID uuid, Long anuncioId) {
        return repositorioFavorito.existsByUsuarioIdAndAnuncioId(uuid, anuncioId);
    }

    public Favorito guardar(Favorito favorito) {
        return repositorioFavorito.save(favorito);
    }

    public Favorito buscarPorId(Long id) {
        return repositorioFavorito.findById(id).orElseThrow(() -> new NoSuchElementException("Favorito con ID " + id + " no encontrado"));
    }

    public void borrar(Favorito favorito) {
        repositorioFavorito.delete(favorito);
    }

    public Page<Favorito> buscarTodos(Pageable pageable) {
        return repositorioFavorito.findAll(pageable);
    }

    public List<Favorito> buscarPorAnuncioId(Long anuncioId) {
        return repositorioFavorito.findByAnuncioId(anuncioId);
    }

}
