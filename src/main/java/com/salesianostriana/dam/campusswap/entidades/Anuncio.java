package com.salesianostriana.dam.campusswap.entidades;

import com.salesianostriana.dam.campusswap.entidades.extras.Condicion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor @NoArgsConstructor
@ToString @Getter @Setter
@Builder
public class Anuncio {

    /*
    * id
    * titulo
    * descripcion
    * precio (nullable si es intercambio o cesión)
    * tipoOperacion (VENTA / INTERCAMBIO / CESION)
    * estado (ACTIVO / PAUSADO / CERRADO)
    * condicion (NUEVO / COMO_NUEVO / USADO / DETERIORADO)
    * fechaPublicacion
    * */

    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private Double precio;
    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;
    private Estado estado;
    private Condicion condicion;

    @CreatedDate
    private LocalDateTime fechaPublicacion;


}
