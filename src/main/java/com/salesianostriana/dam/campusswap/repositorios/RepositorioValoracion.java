package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioValoracion extends JpaRepository<Valoracion, Long>, JpaSpecificationExecutor<Valoracion> {

    @Query("""
            SELECT AVG(v.puntuacion)
            FROM Valoracion v
            WHERE v.evaluado.id = :usuarioId
            """)
    Double calcularMediaValoracionesUsuario(@Param("usuarioId") UUID usuarioId);


    @Query(
            "SELECT COUNT(v) > 0 FROM Valoracion v WHERE v.anuncio.id = :id"
    )
    boolean existsByAnuncioId(Long id);
}
