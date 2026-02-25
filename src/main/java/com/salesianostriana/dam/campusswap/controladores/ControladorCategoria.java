package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioCategoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@Tag(
        name = "Controlador de Categorías",
        description = "Endpoints relacionados con la gestión de categorías de anuncios."
)
public class ControladorCategoria {

    private final ServicioCategoria servicioCategoria;

    @GetMapping
    @Operation(
            summary = "Obtener todas las categorías",
            description = "Endpoint para obtener el listado completo de categorías disponibles en la plataforma. " +
                    "Este endpoint está disponible para usuarios autenticados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categorías obtenidas correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "nombre": "Electrónica e Informática"
                                                },
                                                {
                                                    "id": 2,
                                                    "nombre": "Libros y Material Académico"
                                                },
                                                {
                                                    "id": 3,
                                                    "nombre": "Ropa y Accesorios"
                                                },
                                                {
                                                    "id": 4,
                                                    "nombre": "Deportes y Ocio"
                                                },
                                                {
                                                    "id": 5,
                                                    "nombre": "Hogar y Decoración"
                                                },
                                                {
                                                    "id": 6,
                                                    "nombre": "Música y Entretenimiento"
                                                },
                                                {
                                                    "id": 7,
                                                    "nombre": "Vehículos y Transporte"
                                                },
                                                {
                                                    "id": 8,
                                                    "nombre": "Otros"
                                                }
                                            ]
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado. Se requiere autenticación para acceder a este recurso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Acceso denegado. No se ha proporcionado un token de autenticación válido.",
                                                "instance": "/api/v1/catalogo",
                                                "status": 401,
                                                "title": "No autorizado."
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "403",
            description = "Solicitud prohibida",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se puede reportar un anuncio que te pertenece",
                                                "instance": "/api/v1/anuncios/2",
                                                "status": 403,
                                                "title": "Recurso perteneciente al usuario"
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
                                                "detail": "Ha ocurrido un error inesperado",
                                                "instance": "/api/v1/catalogo",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<List<CategoriaResponseDto>> obtenerTodas() {
        return ResponseEntity.ok(servicioCategoria.obtenerTodas().stream().map(CategoriaResponseDto::of).toList());
    }
}