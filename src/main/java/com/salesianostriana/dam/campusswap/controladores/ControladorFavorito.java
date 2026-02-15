package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.servicios.ServicioFavorito;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ControladorFavorito {

    private final ServicioFavorito servicioFavorito;


}
