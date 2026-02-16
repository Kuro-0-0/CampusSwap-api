package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.servicios.ServicioValoracion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/valoraciones")
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
                    schema = @Schema(implementation = ProblemDetail.class, examples = "Usuario no encontrado"),
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



}
