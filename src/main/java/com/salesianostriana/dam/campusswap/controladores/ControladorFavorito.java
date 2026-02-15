package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.servicios.ServicioFavorito;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favoritos")
public class ControladorFavorito {

    private final ServicioFavorito servicioFavorito;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFavorito(
            @PathVariable Long id,
            @RequestBody String idUsuario
    ) {
        servicioFavorito.eliminarFavorito(id, idUsuario);
        return ResponseEntity.noContent().build();
    }


}
