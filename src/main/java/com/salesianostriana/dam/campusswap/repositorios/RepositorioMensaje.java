package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMensaje extends JpaRepository<Mensaje, Long> {

    @EntityGraph(
            attributePaths = {
                "anuncio",
                "emisor"
            }
    )
    @Query(
            "SELECT m FROM Mensaje m WHERE m.anuncio.id = :idAnuncio"
    )
    Page<Mensaje> findAllByAnuncioId(Pageable pageable, Long idAnuncio);

}

