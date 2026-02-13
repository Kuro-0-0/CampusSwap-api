package com.salesianostriana.dam.campusswap.entidades;

import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
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
    private Condicion condicion;

    private String imagen;

    @CreatedDate
    private LocalDateTime fechaPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne(mappedBy = "anuncio", orphanRemoval = true, fetch = FetchType.LAZY)
    private Valoracion valoracion;

    public Anuncio modificar(Anuncio anuncio) {
        return Anuncio.builder()
                .id(this.id)
                .titulo(anuncio.getTitulo())
                .descripcion(anuncio.getDescripcion())
                .precio(anuncio.getPrecio())
                .tipoOperacion(anuncio.getTipoOperacion())
                .condicion(anuncio.getCondicion())
                .imagen(anuncio.getImagen())
                .estado(this.estado)
                .usuario(this.usuario)
                .valoracion(this.valoracion)
                .build();
    }
}
