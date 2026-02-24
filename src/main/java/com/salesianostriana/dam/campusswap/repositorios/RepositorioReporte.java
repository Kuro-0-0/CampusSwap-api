package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Reporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioReporte extends JpaRepository<Reporte, Long> {
    Optional<Reporte> findByAnuncioIdAndUsuarioId(Long anuncioId, UUID usuarioId);

    List<Reporte> findByAnuncioId(Long id);

    @Override
    @EntityGraph(
            attributePaths = {
                    "anuncio",
                    "anuncio.usuario",
                    "usuario"
            }
    )
    Page<Reporte> findAll(Pageable pageable);
}
