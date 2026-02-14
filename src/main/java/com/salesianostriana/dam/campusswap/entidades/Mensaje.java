package com.salesianostriana.dam.campusswap.entidades;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Mensaje {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;

    @CreatedDate
    private LocalDateTime fechaEnvio;

}
