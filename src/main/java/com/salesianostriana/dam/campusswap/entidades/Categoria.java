package com.salesianostriana.dam.campusswap.entidades;


import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;




@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Categoria {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;


    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "categoria",orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Anuncio> anuncios = new ArrayList<>();




}
