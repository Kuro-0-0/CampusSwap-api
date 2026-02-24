package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Repository
public interface RepositorioAnuncio extends JpaRepository<Anuncio, Long>, JpaSpecificationExecutor<Anuncio> {

    @EntityGraph(attributePaths = {"usuario", "categoria"})
    Page<Anuncio> findByUsuarioId(UUID usuarioId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"usuario", "categoria"})
    <S extends Anuncio, R> R findBy(
            Specification<Anuncio> spec,
            Function<? super SpecificationFluentQuery<S>, R> queryFunction
    );

    @Override
    @EntityGraph(
            attributePaths = {
                    "usuario",
                    "categoria"
            }
    )
    Optional<Anuncio> findById(Long id);

    @EntityGraph(
            attributePaths = {
                    "categoria"
            }
    )
    @Query(
            "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Anuncio a " +
            "WHERE a.categoria.id = :id"
    )
    boolean existByCategoriaId(Long id);
}
