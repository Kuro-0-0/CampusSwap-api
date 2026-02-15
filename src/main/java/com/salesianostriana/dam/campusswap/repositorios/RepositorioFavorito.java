package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioFavorito extends JpaRepository<Favorito, Long>, JpaSpecificationExecutor<Favorito> {

    boolean existsByUsuarioIdAndAnuncioId(UUID usuarioId, Long anuncioId);
}
