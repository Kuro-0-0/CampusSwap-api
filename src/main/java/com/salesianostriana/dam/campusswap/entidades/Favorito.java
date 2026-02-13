package com.salesianostriana.dam.campusswap.entidades;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Favorito {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime fecha;

}
