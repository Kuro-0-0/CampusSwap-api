package com.salesianostriana.dam.campusswap.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Valoracion {

    @Id
    @GeneratedValue
    private Long id;

    private Double puntuacion;
    private String comentario;

    @CreatedDate
    private LocalDateTime fecha;
}
