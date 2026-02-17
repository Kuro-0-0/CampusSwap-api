package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioValoracion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioBaseValoracion {

    private final RepositorioValoracion repositorioValoracion;

    public Double calcularMediaValoracionesUsuario(UUID id) {
        return repositorioValoracion.calcularMediaValoracionesUsuario(id);
    }

    public boolean existePorAnuncioId(Long id) {
        return repositorioValoracion.existsByAnuncioId(id);
    }

    public Valoracion guardar(Valoracion valoracion) {
        return repositorioValoracion.save(valoracion);
    }

    public Page<Valoracion> buscarPorEvaluadoId(UUID uuid, Pageable pageable) {
        return repositorioValoracion.findByEvaluadoId(uuid, pageable);
    }
}
