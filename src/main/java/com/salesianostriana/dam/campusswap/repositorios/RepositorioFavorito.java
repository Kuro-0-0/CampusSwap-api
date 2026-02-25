package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Favorito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositorioFavorito extends JpaRepository<Favorito, Long>, JpaSpecificationExecutor<Favorito> {

    boolean existsByUsuarioIdAndAnuncioId(UUID usuarioId, Long anuncioId);

    List<Favorito> findByAnuncioId(Long anuncioId);

    @EntityGraph(
            attributePaths = {"usuario", "anuncio"}
    )
    Page<Favorito> findAllByUsuarioId(Pageable pageable, UUID id);

    @EntityGraph(
            attributePaths = {"usuario"}
    )
    Optional<Favorito> findById(Long id);

    @EntityGraph(
            attributePaths = {"anuncio"}
    )
    Boolean existsByAnuncioId(Long anuncioId);
}
