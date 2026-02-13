package com.salesianostriana.dam.campusswap.entidades;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double puntuacion;
    private String comentario;

    @CreatedDate
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluador_id")
    private Usuario evaluador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluado_id")
    private Usuario evaluado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;
}
