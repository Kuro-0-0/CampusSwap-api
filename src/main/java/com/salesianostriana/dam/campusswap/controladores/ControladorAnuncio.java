package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.AnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioAnuncio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/anuncios")
public class ControladorAnuncio {

    private final ServicioAnuncio servicioAnuncio;

    @PostMapping
    @ApiResponse(
            responseCode = "201",
            description = "Anuncio creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnuncioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                            "id": 4,
                                                "titulo": "Laptop Dell XPS 15",
                                                "descripcion": "Laptop en excelente estado, poco uso, incluye cargador",
                                                "precio": null,
                                                "categoria": "Portátiles",
                                                "imagen": "https://ejemplo.com/laptop.jpg",
                                                "tipoOperacion": "INTERCAMBIO",
                                                "estado": "ACTIVO",
                                                "condicion": "COMO_NUEVO",
                                                "usuarioId": "a89f6904-4624-47bf-9967-be2e66aff7a2"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "El precio debe ser mayor que 0 para venta y null para cesión o intercambio",
                                                "instance": "/api/v1/anuncios",
                                                "status": 400,
                                                "title": "Solicitud inválida"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Entidad no encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado la entidad con id: X",
                                                "instance": "/api/v1/anuncios",
                                                "status": 404,
                                                "title": "Recurso no encontrado"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Ha ocurrido un error inesperado al procesar la solicitud",
                                                "instance": "/api/v1/anuncios",
                                                "status": 500,
                                                "title": "Error interno del servidor"
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Crear un nuevo anuncio",
            description = "Permite crear un nuevo anuncio en el sistema."
    )
    public ResponseEntity<AnuncioResponseDto> crearAnuncio(@Valid @RequestBody AnuncioRequestDto dto){
        Anuncio nuevoAnuncio = dto.toAnuncio();

        return ResponseEntity.status(HttpStatus.CREATED).body(AnuncioResponseDto.of(servicioAnuncio.crearAnuncio(nuevoAnuncio)));
    }
}
