package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion.CrearValoracionResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion.ValoracionRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion.ValoracionResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/valoraciones")
@Tag(
        name = "Controlador para la gestión de valoraciones",
        description = "Operaciones relacionadas con las valoraciones de los usuarios"
)
public class ControladorValoracion {

    private final ServicioValoracion servicioValoracion;

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener la media de valoraciones de un usuario",
            description = "Devuelve la media de las valoraciones recibidas por un usuario específico."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Media de valoraciones obtenida correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Double.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "media": 4.5
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
                                                 "detail": "UUID string too large",
                                                 "instance": "/api/v1/valoraciones/504f42a0-6869-44db-a2c5-d07e75483d953",
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
            description = "Usuario no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Usuario no encontrado",
                                                "instance": "/api/v1/valoraciones/504f42a0-6869-44db-a2c5-d07e75483d95",
                                                "status": 404,
                                                "title": "Recurso no encontrado"
                                            }
                                            """
                            )

            }

            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El usuario no tiene valoraciones",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                  "detail": "El usuario no tiene valoraciones",
                                                   "instance": "/api/v1/valoraciones/76f2606b-b61e-4067-909f-c0de543ff05f",
                                                   "status": 409,
                                                   "title": "Estado no válido"
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
                                                 "instance": "/api/v1/valoraciones/70a8fa74-0461-4b9d-ae5c-4bb37f8a2580",
                                                 "status": 500,
                                                 "title": "Error inesperado."
                                             }
                                            """
                            )
            }
            )
    )
    public ResponseEntity<Double> obtenerMediaValoraciones (@PathVariable String id) {

        Double media = servicioValoracion.calcularMediaValoraciones(id);

        return ResponseEntity.ok(media);

    }

    @GetMapping("/usuario/{usuarioId}")
    @ApiResponse(
            responseCode = "200",
            description = "Valoraciones obtenidas correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                             {
                                                 "content": [
                                                     {
                                                         "id": 1,
                                                         "puntuacion": 5.0,
                                                         "comentario": "El libro estaba impecable, tal como decía el anuncio. Carlos es muy amable.",
                                                         "fecha": "2026-02-16 20:16:49",
                                                         "evaluadorNombre": "Laura Compradora",
                                                         "fotoPerfilEvaluador": "laura.jpg",
                                                         "anuncioTitulo": "Clean Code - Robert C. Martin"
                                                     }
                                                 ],
                                                 "page": {
                                                     "size": 10,
                                                     "number": 0,
                                                     "totalElements": 1,
                                                     "totalPages": 1
                                                 }
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
                                                  "detail": "UUID string too large",
                                                  "instance": "/api/v1/valoraciones/usuario/9c9cb8c5-0a3d-4291-b96e-4c3bb4cf1ed11",
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
            description = "Usuario no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado el usuario con id: 9c9cb8c5-0a3d-4291-b96e-4c3bb4cf1ed1",
                                                "instance": "/api/v1/valoraciones/usuario/9c9cb8c5-0a3d-4291-b96e-4c3bb4cf1ed1",
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
                                                 "detail": "Ha ocurrido un error inesperado",
                                                 "instance": "/api/v1/valoraciones/usuario/9c9cb8c5-0a3d-4291-b96e-4c3bb4cf1ed1",
                                                 "status": 500,
                                                 "title": "Error inesperado."
                                             }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Obtener las valoraciones de un usuario",
            description = "Devuelve una lista paginada de las valoraciones recibidas por un usuario específico, ordenadas por fecha de creación."
    )
    public ResponseEntity<Page<ValoracionResponseDto>> obtenerValoraciones(@Parameter(description = "ID UUID del usuario",example= "550e8400-e29b-41d4-a716-446655440000")
                                                                           @PathVariable String usuarioId,
                                                                           @Parameter(description = "Parámetros de paginación y ordenación")
                                                                           @PageableDefault(size = 10,sort = "fecha",direction = Sort.Direction.DESC)
                                                                           Pageable pageable){
        return ResponseEntity.ok(servicioValoracion.obtenerValoraciones(pageable,usuarioId).map(ValoracionResponseDto::of));
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva valoración",
            description = "Permite crear una nueva valoración para un anuncio cerrado."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Valoración creada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CrearValoracionResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "puntuacion": 2.0,
                                                "comentario": "Bastante desagradable el vendedor...",
                                                "nombreEvaluador": "Laura Compradora",
                                                "nombreEvaluado": "Carlos Vendedor",
                                                "tituloAnuncio": "Clean Code - Robert C. Martin",
                                                "fecha": "2026-02-16 20:52:23"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse (
            responseCode = "400",
            description = "Solicitud incorrecta",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Invalid UUID string: 1",
                                                "instance": "/api/v1/valoraciones",
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
                                                "detail": "No se ha encontrado el anuncio con ID: 67",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 404,
                                                "title": "Recurso no encontrado"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Estado incorrecto",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Este anuncio ya ha sido valorado",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 409,
                                                "title": "Estado no válido"
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
                                                "instance": "/api/v1/valoraciones",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<CrearValoracionResponseDto> crearValoracion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para crear una valoración",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValoracionRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "puntuacion" : "2",
                                                        "comentario": "Bastante desagradable el vendedor...",
                                                        "idAnuncio": "4",
                                                        "idEvaluador": "2e65aadb-c876-458a-9ce6-9107fb65b409"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @Valid @RequestBody ValoracionRequestDto valoracionRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CrearValoracionResponseDto.of(
                        servicioValoracion.crearValoracion(valoracionRequestDto.to())
                )
        );
    }


}
