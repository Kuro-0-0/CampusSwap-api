package com.salesianostriana.dam.campusswap.controladores;


import com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito.FavoritoRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito.FavoritoResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioFavorito;
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
@RequestMapping("/api/v1/favoritos")
public class ControladorFavorito {

    private final ServicioFavorito servicioFavorito;

    @PostMapping()
    @ApiResponse(
            responseCode = "201",
            description = "Favorito creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FavoritoResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "nombreUsuario": "juanperez",
                                                "tituloAnuncio": "Libro de matemáticas",
                                                "fechaFavorito": "2024-06-15T14:30:00"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud incorrecta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Ya has marcado este anuncio como favorito",
                                                "instance": "/api/v1/favoritos",
                                                "status": 400,
                                                "title": "Argumento no válido"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Recurso no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado el anuncio con id: 5",
                                                "instance": "/api/v1/favoritos",
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
                                                "detail": "Error al crear el favorito",
                                                "instance": "/api/v1/favoritos",
                                                "status": 500,
                                                "title": "Error interno del servidor"
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Crear un nuevo favorito",
            description = "Permite a un usuario marcar un anuncio como favorito. Se requiere el ID del usuario y el ID del anuncio."
    )
    public ResponseEntity<FavoritoResponseDto> crearFavorito(@Valid @RequestBody() FavoritoRequestDto favoritoRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(FavoritoResponseDto.of(servicioFavorito.crearFavorito(favoritoRequestDto.anuncioId(), favoritoRequestDto.usuarioId())));
    }


}
