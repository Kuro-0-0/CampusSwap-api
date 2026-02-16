package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositorioReporte extends JpaRepository<Reporte, Long> {
    Optional<Reporte> findByAnuncioIdAndUsuarioId(Long anuncioId, UUID usuarioId);
}
