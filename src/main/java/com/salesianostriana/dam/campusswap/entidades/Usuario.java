package com.salesianostriana.dam.campusswap.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;
    private String email;
    private String contrasena;
    private String fotoPerfil;
    private String descripcion;
    private double reputacionMedia;

    @CreatedDate
    private LocalDateTime fechaRegistro;

    private Boolean activo;

}
