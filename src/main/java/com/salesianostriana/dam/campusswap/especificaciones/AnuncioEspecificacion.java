package com.salesianostriana.dam.campusswap.especificaciones;

import ch.qos.logback.core.CoreConstants;
import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
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

    public static PredicateSpecification<Anuncio> porCategoria(Long categoriaId) {
        return ((from, criteriaBuilder) -> {
            if (categoriaId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(from.get("categoria").get("id"), categoriaId);
        });
    }

    public static PredicateSpecification<Anuncio> porPrecioRango(Double minPrecio, Double maxPrecio) {
        return (from, builder) ->{

            if(minPrecio == null && maxPrecio == null){
                return builder.and();
            }

            Double realMin = (minPrecio != null) ? minPrecio : 0.0;
            Double realMax = (maxPrecio != null) ? maxPrecio : Double.MAX_VALUE;

            if(from.get("precio") == null){
                return builder.and();
            }

            return builder.between(from.get("precio"), realMin, realMax);
        };
    }

    public static PredicateSpecification<Anuncio> porTipoOperacion(TipoOperacion tipoOperacion) {
        return (from, builder) -> {
            if (tipoOperacion == null) {
                return builder.conjunction();
            }
            return builder.equal(from.get("tipoOperacion"), tipoOperacion);
        };
    }

    public static PredicateSpecification<Anuncio> porEstado(Estado estado){
        return (from, builder) -> {
            if (estado == null) {
                return builder.conjunction();
            }
            return builder.equal(from.get("estado"), estado);
        };
    }

}
