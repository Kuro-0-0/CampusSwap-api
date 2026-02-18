package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.EnviarMensajeRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.MensajeResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.ListarMensajeResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioMensaje;
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
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    @PreAuthorize(
        "#dto.receptorId != principal.id"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Mensaje enviado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MensajeResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 3,
                                                "contenido": "mensaje",
                                                "fechaEnvio": "2026-02-16T17:01:15.852592700",
                                                "anuncioId": 1,
                                                "emisorId": "7e631478-7d11-48fd-9e4f-7d18a3bb753a",
                                                "receptorId": "e7c93db8-f0be-4692-9e55-68529a0533fd"
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
                                                "detail": "Argumento no válido",
                                                "instance": "/api/v1/mensajes",
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
                                                "detail": "No se ha encontrado la entidad con id: X",
                                                "instance": "/api/v1/mensajes",
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
                                                "instance": "/api/v1/mensajes",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Enviar mensaje",
            description = "Permite a un usuario enviar un mensaje a otro usuario relacionado con un anuncio específico."
    )
    public ResponseEntity<MensajeResponseDto> enviarMensaje(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DTO con los datos para enviar un mensaje",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EnviarMensajeRequestDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "contenido": "mensaje",
                                                "anuncioId": 1,
                                                "emisorId": "d5e7f9e8-e0fb-4482-9b38-e24882e1a263",
                                                "receptorId": "78aff3fb-7752-44ae-9dcd-a9d57605fc68"
                                            }
                                            """
                            )
                    }

            )
        ) @RequestBody EnviarMensajeRequestDto dto,
            @AuthenticationPrincipal Usuario usuario
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(MensajeResponseDto.of(servicio.enviarMensaje(EnviarMensajeRequestDto.from(dto), usuario)));
    }

    @GetMapping("/{idAnuncio}")
    @PreAuthorize("@comprobarMensaje.esParticipante(#idAnuncio, principal)")
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
