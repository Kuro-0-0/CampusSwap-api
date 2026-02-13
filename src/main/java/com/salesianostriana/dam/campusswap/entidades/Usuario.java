package com.salesianostriana.dam.campusswap.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.expression.spel.ast.BooleanLiteral;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
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
    private LocalDateTime fechaRegistro;
    private Boolean activo;

}
