package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.ListarMensajeResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioMensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mensajes")
public class ControladorMensaje {

    private final ServicioMensaje servicio;

    @GetMapping("/{idAnuncio}")
    public Page<ListarMensajeResponseDto> obtenerMensajes(
            @PathVariable Long idAnuncio,
            Pageable pageable
    ) {
        return servicio.obtenerMensajes(idAnuncio, pageable).map(ListarMensajeResponseDto::of);
    }

}
