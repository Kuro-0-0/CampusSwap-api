package com.salesianostriana.dam.campusswap.entidades;

import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Anuncio {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private Double precio;
    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @Enumerated(EnumType.STRING)
    private Condicion condicion;

    private String imagen;

    @CreatedDate
    private LocalDateTime fechaPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToOne(mappedBy = "anuncio", orphanRemoval = true, fetch = FetchType.LAZY)
    private Valoracion valoracion;

    public Anuncio modificar(Anuncio anuncio) {
        this.titulo = anuncio.getTitulo();
        this.descripcion = anuncio.getDescripcion();
        this.precio = anuncio.getPrecio();
        this.tipoOperacion = anuncio.getTipoOperacion();
        this.condicion = anuncio.getCondicion();
        this.imagen = anuncio.getImagen();
        return this;

    }
}
