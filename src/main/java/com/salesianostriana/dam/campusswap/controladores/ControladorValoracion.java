package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion.CrearValoracionResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion.ValoracionRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.valoracion.ValoracionResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioValoracion;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/media")
    @Operation(
            summary = "Obtener la media de valoraciones de un usuario",
            description = "Devuelve la media de las valoraciones recibidas por el usuario autenticado."
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
            responseCode = "401",
            description = "No autorizado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                 "detail": "Full authentication is required to access this resource",
                                                 "instance": "/api/v1/valoraciones",
                                                 "status": 401,
                                                 "title": "Unauthorized"
                                             }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                 "detail": "Access is denied",
                                                 "instance": "/api/v1/valoraciones",
                                                 "status": 403,
                                                 "title": "Forbidden"
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
                                                "instance": "/api/v1/valoraciones/",
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
                                                   "instance": "/api/v1/valoraciones/",
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
                                                 "instance": "/api/v1/valoraciones/",
                                                 "status": 500,
                                                 "title": "Error inesperado."
                                             }
                                            """
                            )
            }
            )
    )
    public ResponseEntity<Double> obtenerMediaValoraciones (@AuthenticationPrincipal Usuario usuario) {

        Double media = servicioValoracion.calcularMediaValoraciones(usuario);

        return ResponseEntity.ok(media);

    }

    @GetMapping("/{id}")
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
            responseCode = "401",
            description = "No autorizado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Full authentication is required to access this resource",
                                                "instance": "/api/v1/valoraciones/media",
                                                "status": 401,
                                                "title": "Unauthorized"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                 "detail": "Access is denied",
                                                 "instance": "/api/v1/valoraciones/media",
                                                 "status": 403,
                                                 "title": "Forbidden"
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
                                                "instance": "/api/v1/valoraciones/media",
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
                                                 "instance": "/api/v1/valoraciones/media",
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
            description = "Devuelve una lista paginada de las valoraciones recibidas por el usuario autenticado, ordenadas por fecha de creación."
    )
    public ResponseEntity<Page<ValoracionResponseDto>> obtenerValoraciones(
                                                                           @PathVariable("id") String idUsuario,
                                                                           @Parameter(description = "Parámetros de paginación y ordenación")
                                                                           @PageableDefault(size = 10,sort = "fecha",direction = Sort.Direction.DESC)
                                                                           Pageable pageable){
        return ResponseEntity.ok(servicioValoracion.obtenerValoraciones(pageable,idUsuario).map(ValoracionResponseDto::of));
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
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud incorrecta por datos de entrada inválidos",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    name = "Puntuación nula",
                                    value = """
                                            {
                                                "detail": "puntuacion: La puntuación no puede ser nula",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 400,
                                                "title": "Argumento no válido"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "Puntuación fuera de rango",
                                    value = """
                                            {
                                                "detail": "puntuacion: La puntuación no puede ser mayor que 5",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 400,
                                                "title": "Argumento no válido"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "Comentario nulo",
                                    value = """
                                            {
                                                "detail": "comentario: El comentario no puede estar vacío",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 400,
                                                "title": "Argumento no válido"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "Comentario fuera de tamaño",
                                    value = """
                                            {
                                                "detail": "comentario: El comentario debe tener entre 10 y 500 caracteres",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 400,
                                                "title": "Argumento no válido"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "ID de anuncio nulo",
                                    value = """
                                            {
                                                "detail": "idAnuncio: no puede ser nulo",
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
            responseCode = "401",
            description = "No autorizado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Full authentication is required to access this resource",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 401,
                                                "title": "Unauthorized"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Access is denied",
                                                "instance": "/api/v1/valoraciones",
                                                "status": 403,
                                                "title": "Forbidden"
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
                                                        "idAnuncio": "4"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @Valid @RequestBody ValoracionRequestDto valoracionRequestDto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CrearValoracionResponseDto.of(
                        servicioValoracion.crearValoracion(valoracionRequestDto.to(), usuario)
                )
        );
    }

    @GetMapping("/check/{anuncioId}")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
    public ResponseEntity<Boolean> checkValoracion(@PathVariable Long anuncioId) {
        return ResponseEntity.ok(servicioValoracion.checkValoracion(anuncioId));
    }


}
