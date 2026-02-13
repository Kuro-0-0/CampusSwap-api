package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.crear.CrearAnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioAnuncio;
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

    private final ServicioAnuncio servicio;

    @PostMapping
    public ResponseEntity<AnuncioResponseDto> crearAnuncio(CrearAnuncioRequestDto dto){
        Anuncio nuevoAnuncio = dto.toAnuncio();

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnuncioResponseDto> editarAnuncio(Long id, CrearAnuncioRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(AnuncioResponseDto.of(
            servicio.editarAnuncio(id, dto.toAnuncio())
        ));
    }

}
