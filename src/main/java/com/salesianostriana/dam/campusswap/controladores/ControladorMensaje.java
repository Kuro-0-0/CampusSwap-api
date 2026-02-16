package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.ListarMensajeResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioMensaje;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mensajes")
@Tag(
        name = "Controlador Mensajes",
        description = "Controlador para gestionar el envío de mensajes entre usuarios relacionados con anuncios."
)
public class ControladorMensaje {

    private final ServicioMensaje servicio;


    @GetMapping("/{idAnuncio}")
    @Operation(
            summary = "Obtener mensajes de un anuncio",
            description = "Obtiene una lista paginada de mensajes asociados a un anuncio específico."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Mensajes obtenidos correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListarMensajeResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "idEmisor": "6537fd11-87ff-40ba-a85a-3886b648036e",
                                                        "mensaje": "Hola Carlos, ¿sigue disponible el portátil? ¿El precio es negociable?",
                                                        "fechaMensaje": "2026-02-16 17:02:54"
                                                    },
                                                    {
                                                        "idEmisor": "8dad8f34-1c09-4cef-a072-a554b12cd56e",
                                                        "mensaje": "Hola Laura, sí, sigue disponible. Podría bajarlo a 630€ si vienes hoy.",
                                                        "fechaMensaje": "2026-02-16 17:02:54"
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
            responseCode = "404",
            description = "Anuncio no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado el anuncio con id: 1231234",
                                                "instance": "/api/v1/mensajes/1231234",
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
                                                "instance": "/api/v1/mensajes/1",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    public Page<ListarMensajeResponseDto> obtenerMensajes(
            @Parameter(
                    description = "ID del anuncio para el cual se desean obtener los mensajes",
                    example = "1",
                    required = true
            ) @PathVariable Long idAnuncio,
            Pageable pageable
    ) {
        return servicio.obtenerMensajes(idAnuncio, pageable).map(ListarMensajeResponseDto::of);
    }

}
