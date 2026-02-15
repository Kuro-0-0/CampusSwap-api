package com.salesianostriana.dam.campusswap.especificaciones;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import org.springframework.data.jpa.domain.PredicateSpecification;

public class AnuncioEspecificacion {

    public static PredicateSpecification<Anuncio> buscarPorQuery(String q) {
        return ((from, criteriaBuilder) -> {
            if(q == null || q.isBlank()){
                return criteriaBuilder.conjunction();
            }

            String likePattern = "%" + q.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(from.get("titulo")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(from.get("descripcion")), likePattern)
            );
        });
    }

    public static PredicateSpecification<Anuncio> soloActivos() {
        return ((from, criteriaBuilder) ->
                criteriaBuilder.equal(from.get("estado"), Estado.ACTIVO)
        );
    }

}
