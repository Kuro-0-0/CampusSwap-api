package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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
    Page<Mensaje> findAllByAnuncioId(Long idAnuncio, Pageable pageable);

    @EntityGraph(
            attributePaths = {
                    "anuncio",
                    "emisor",
                    "receptor"
            }
    )
    @Query(
            "SELECT m FROM Mensaje m WHERE m.anuncio.id = :idAnuncio AND (m.emisor.id = :idUsuario OR m.receptor.id = :idUsuario)"
    )
    List<Mensaje> findAllByAnuncioIdAndUsuarioId(Long idAnuncio, UUID idUsuario);
}

