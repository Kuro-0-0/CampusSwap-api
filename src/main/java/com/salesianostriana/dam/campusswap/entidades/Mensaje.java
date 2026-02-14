package com.salesianostriana.dam.campusswap.entidades;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class Mensaje {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;

    @CreatedDate
    private LocalDateTime fechaEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;





}
