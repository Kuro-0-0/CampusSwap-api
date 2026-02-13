package com.salesianostriana.dam.campusswap.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ControladorAnuncio {

    @PutMapping("/anuncios/{id}")
    public ResponseEntity<Object> editarAnuncio(
            @PathVariable Long id,
            @RequestBody Object anuncioDto
    ) {
        return null;
    }

}
