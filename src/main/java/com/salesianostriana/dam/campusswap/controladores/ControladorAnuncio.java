package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.AnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.AnuncioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/anuncios")
public class ControladorAnuncio {

    @PostMapping
    public ResponseEntity<AnuncioResponseDto> crearAnuncio(AnuncioRequestDto dto){
        Anuncio nuevoAnuncio = dto.toAnuncio();

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnuncioResponseDto> editarAnuncio(Long id, AnuncioRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
