package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.AnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.BorrarAnuncioRequestDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioAnuncio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/anuncios")
public class ControladorAnuncio {

    private final ServicioAnuncio servicioAnuncio;

    @PostMapping
    public ResponseEntity<AnuncioResponseDto> crearAnuncio(@RequestBody AnuncioRequestDto dto){
        Anuncio nuevoAnuncio = dto.toAnuncio();

        return ResponseEntity.status(HttpStatus.CREATED).body(AnuncioResponseDto.of(servicioAnuncio.crearAnuncio(nuevoAnuncio)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAnuncio(@RequestBody BorrarAnuncioRequestDto dto , @PathVariable Long id ){
        servicioAnuncio.borrarAnuncio(id,dto.usuarioId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
