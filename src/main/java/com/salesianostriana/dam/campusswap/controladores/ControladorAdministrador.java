package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaRequestUpdateDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioAdministrador;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ControladorAdministrador {

    private final ServicioAdministrador servicioAdministrador;

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponseDto> crearCategoria(
            @Valid @RequestBody CategoriaRequestDto categoriaRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CategoriaResponseDto.of(
                    servicioAdministrador.crearCategoria(categoriaRequestDto.toCategoria())
                )
        );
    }

    @GetMapping("/categorais/{id}")
    public ResponseEntity<CategoriaResponseDto> obtenerCategoria(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(
                CategoriaResponseDto.of(
                        servicioAdministrador.obtenerCategoria(id)
                )
        );
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseDto> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestUpdateDto categoriaRequestDto
    ) {
        return ResponseEntity.ok().body(
            CategoriaResponseDto.of(
                    servicioAdministrador.actualizarCategoria(id, categoriaRequestDto.toCategoria())
            )
        );
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @PathVariable Long id
    ) {
        servicioAdministrador.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias")
    public ResponseEntity<Page<CategoriaResponseDto>> listarCategorias(
            @RequestParam(required = false) String nombre,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(
            servicioAdministrador.listarCategorias(nombre,pageable).map(CategoriaResponseDto::of)
        );
    }




}
