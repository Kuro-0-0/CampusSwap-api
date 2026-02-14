package com.salesianostriana.dam.campusswap.entidades;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Builder.Default
    @OneToMany(mappedBy = "usuario", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Anuncio> anuncios = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "evaluador", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Valoracion> valoracionesEnviadas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "evaluado", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Valoracion> valoracionesRecibidas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "usuario", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Favorito> favoritos = new ArrayList<>();

    public void agregarAnuncio(Anuncio anuncio) {
        anuncios.add(anuncio);
        anuncio.setUsuario(this);
    }

    public void borrarAnuncio(Anuncio anuncio){
        anuncios.remove(anuncio);
        anuncio.setUsuario(null);
    }

}
