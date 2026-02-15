package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito.FavoritoRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito.FavoritoResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioFavorito;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favoritos")
@Tag(
        name = "Controlador Favorito",
        description = "Endpoints relacionados con la gestión de favoritos"
)
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

    @DeleteMapping("/{id}")
    @ApiResponse(
            responseCode = "204",
            description = "No Content - Favorito eliminado correctamente"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Forbidden - El usuario no es el propietario del favorito",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No puedes eliminar un favorito que no es tuyo",
                                                "instance": "/api/v1/favoritos/1",
                                                "status": 403,
                                                "title": "Recurso no perteneciente al usuario"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found - No se ha encontrado el favorito con el ID proporcionado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado el favorito con id: 1",
                                                "instance": "/api/v1/favoritos/1",
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
            description = "Internal Server Error - Error al eliminar el favorito",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Ha ocurrido un error inesperado",
                                                "instance": "/api/v1/favoritos/1",
                                                "status": 500,
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Eliminar un favorito",
            description = "Elimina un favorito específico. Solo el usuario que creó el favorito puede eliminarlo."
    )
    public ResponseEntity<?> eliminarFavorito(
            @Parameter(
                    description = "ID del favorito a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @RequestBody String idUsuario
    ) {
        servicioFavorito.eliminarFavorito(id, idUsuario);
        return ResponseEntity.noContent().build();
    }


}
