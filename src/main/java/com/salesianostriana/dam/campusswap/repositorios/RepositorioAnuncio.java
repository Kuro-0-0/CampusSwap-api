package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioAnuncio extends JpaRepository<Anuncio, Long>, JpaSpecificationExecutor<Anuncio> {
    Page<Anuncio> findByUsuarioId(UUID usuarioId, Pageable pageable);
}
