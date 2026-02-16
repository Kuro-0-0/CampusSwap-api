package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.servicios.ValoracionServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/valoraciones")
public class ControladorValoracion {

    private final ValoracionServicio valoracionServicio;

    @GetMapping("/{id}")
    public ResponseEntity<Double> obtenerMediaValoraciones (@PathVariable String id) {

        Double media = valoracionServicio.calcularMediaValoraciones(id);

        return ResponseEntity.ok(media);



    }



}
