package com.salesianostriana.dam.campusswap.especificaciones;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.PredicateSpecification;

import java.time.LocalDateTime;


public interface MensajeEspecification {

    static PredicateSpecification<Mensaje> porTituloDeAnuncio(String titulo){
        return ((from, criteriaBuilder) -> (titulo == null)
                ? null : criteriaBuilder.like(criteriaBuilder.lower(from.get("anuncio").get("titulo")), "%" + titulo.toLowerCase() + "%"));
    }


     static PredicateSpecification<Mensaje> porNombreParticipante(String nombre) {
        return (from, criteriaBuilder) -> {
            if (nombre == null || nombre.isBlank()) {
                return null;
            }

            String patron = "%" + nombre.toLowerCase() + "%";

            Predicate emisorNombre = criteriaBuilder.like(
                    criteriaBuilder.lower(from.get("emisor").get("nombre")), patron
            );
            Predicate emisorUsername = criteriaBuilder.like(
                    criteriaBuilder.lower(from.get("emisor").get("username")), patron
            );
            Predicate receptorNombre = criteriaBuilder.like(
                    criteriaBuilder.lower(from.get("receptor").get("nombre")), patron
            );
            Predicate receptorUsername = criteriaBuilder.like(
                    criteriaBuilder.lower(from.get("receptor").get("username")), patron
            );

            return criteriaBuilder.or(emisorNombre, emisorUsername, receptorNombre, receptorUsername);
        };
    }

    static PredicateSpecification<Mensaje> porRangoFechas(LocalDateTime desde, LocalDateTime hasta){
        return ((from, criteriaBuilder) -> {
            if(desde == null && hasta == null) return null;

            if(desde !=null && hasta != null){
                return criteriaBuilder.between(from.get("fechaEnvio"),desde,hasta);
            }
            if(desde != null){
                return criteriaBuilder.greaterThanOrEqualTo(from.get("fechaEnvio"),desde);
            }
            return criteriaBuilder.lessThanOrEqualTo(from.get("fechaEnvio"),hasta);
        });
    }

}
