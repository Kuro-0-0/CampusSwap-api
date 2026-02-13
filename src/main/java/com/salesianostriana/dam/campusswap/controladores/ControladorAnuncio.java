package com.salesianostriana.dam.campusswap.controladores;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/anuncios")
public class ControladorAnuncio {

    @PostMapping
    public ResponseEntity<Anun>
}
