package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorioMensaje extends JpaRepository<Mensaje, Long>, JpaSpecificationExecutor<Mensaje> {

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

    @EntityGraph(
            attributePaths = {
                    "anuncio",
                    "emisor",
                    "receptor"
            }
    )
    @Query(
            "SELECT m FROM Mensaje m WHERE m.emisor.id = :id OR m.receptor.id = :id"
    )
    List<Mensaje> findAllByUsuarioId(UUID id);

    @EntityGraph(
            attributePaths = {
                    "anuncio",
                    "emisor",
                    "receptor"
            }
    )
    @Query(
            "SELECT m FROM Mensaje m WHERE m.anuncio.id = :idAnuncio AND ((m.emisor.id = :idActual AND m.receptor.id = :idContrario) OR (m.emisor.id = :idContrario AND m.receptor.id = :idActual))"
    )
    List<Mensaje> findAllByAnuncioIdAndParticipantes(Long idAnuncio, UUID idContrario, UUID idActual);
}

