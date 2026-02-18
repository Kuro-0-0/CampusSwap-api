package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito.FavoritoRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito.FavoritoResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioFavorito;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            description = "Permite al usuario autenticado marcar un anuncio como favorito. En el cuerpo de la solicitud solo es necesario enviar el ID del anuncio; el usuario se obtiene a partir de la autenticación."
    )
    @PreAuthorize(
            "@comprobarAnuncio.esPropietario(#favoritoRequestDto.anuncioId, principal) == false"
    )
    public ResponseEntity<FavoritoResponseDto> crearFavorito(
            @Valid @RequestBody FavoritoRequestDto favoritoRequestDto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(FavoritoResponseDto.of(servicioFavorito.crearFavorito(favoritoRequestDto.anuncioId(), usuario)));
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
    @PreAuthorize(
            "@comprobarFavorito.esPropietario(#id, principal)"
    )
    public ResponseEntity<?> eliminarFavorito(
            @Parameter(
                    description = "ID del favorito a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
            ) {
        servicioFavorito.eliminarFavorito(id, usuario);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    @Operation(
            summary = "Listar favoritos",
            description = "Obtiene una lista paginada de los favoritos."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK - Lista de favoritos obtenida correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FavoritoResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "nombreUsuario": "Pepe Novato",
                                                        "tituloAnuncio": "Bicicleta de montaña Rockrider",
                                                        "fechaFavorito": "2026-02-16T13:25:19.473785"
                                                    },
                                                    {
                                                        "nombreUsuario": "Pepe Novato",
                                                        "tituloAnuncio": "Portátil HP Victus 16GB RAM",
                                                        "fechaFavorito": "2026-02-16T13:25:19.475755"
                                                    }
                                                ],
                                                "page": {
                                                    "size": 20,
                                                    "number": 0,
                                                    "totalElements": 2,
                                                    "totalPages": 1
                                                }
                                            }
                                            """
                            )
                    }
            )
    )

    @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error - Error al listar favoritos",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Ha ocurrido un error inesperado", 
                                                "instance": "/api/v1/favoritos",
                                                "tile": "Error interno del servidor",
                                                "status": 500,
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<Page<FavoritoResponseDto>> listarFavoritos(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(servicioFavorito.listarFavoritos(pageable).map(FavoritoResponseDto::of));
    }

}
