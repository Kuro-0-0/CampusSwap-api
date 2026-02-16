package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.EnviarMensajeRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.MensajeResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioMensaje;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-16T17:01:15.852592700",
                                                "status": 400,
                                                "error": "Bad Request",
                                                "message": "El contenido no puede estar vacío",
                                                "path": "/api/v1/mensajes"
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
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-16T17:01:15.852592700",
                                                "status": 404,
                                                "error": "Not Found",
                                                "message": "Anuncio no encontrado",
                                                "path": "/api/v1/mensajes"
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
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-16T17:01:15.852592700",
                                                "status": 500,
                                                "error": "Internal Server Error",
                                                "message": "Error al enviar el mensaje",
                                                "path": "/api/v1/mensajes"
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
    ) @RequestBody EnviarMensajeRequestDto dto) {
        Mensaje mensaje = servicio.enviarMensaje(EnviarMensajeRequestDto.from(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(MensajeResponseDto.of(mensaje));
    }
}
