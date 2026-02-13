package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.crear.CrearAnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.editar.EditarAnuncioRequestDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioAnuncio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/anuncios")
@Tag(
        name = "Controlador para la gestion global de los anuncios",
        description = "Permite realizar las acciones especificadas sobre los anuncios."
)
public class ControladorAnuncio {

    private final ServicioAnuncio servicio;

    @PostMapping
    public ResponseEntity<AnuncioResponseDto> crearAnuncio(CrearAnuncioRequestDto dto){
        Anuncio nuevoAnuncio = dto.toAnuncio();

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Anuncio editado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnuncioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "titulo": "Producto de Prueba2",
                                                "descripcion": "Una descripción detallada del producto",
                                                "precio": 25.5,
                                                "categoria": "sin categoria",
                                                "imagen": "producto.jpg",
                                                "tipoOperacion": "VENTA",
                                                "estado": "ACTIVO",
                                                "condicion": "NUEVO",
                                                "usuarioId": "6e44a229-0400-4903-9f58-11c63a1dc31a"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud invalida",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No puedes modificar un anuncio que no es tuyo",
                                                "instance": "/api/v1/anuncios/1",
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
            description = "Anuncio no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado el anuncio con id: 5",
                                                "instance": "/api/v1/anuncios/5",
                                                "status": 404,
                                                "title": "Recurso no encontrado"
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Actualiza un anuncio existente",
            description = "Permite editar un anuncio existente. " +
                    "Solo el propietario del anuncio puede editarlo, y el anuncio no puede estar cerrado."
    )
    public ResponseEntity<AnuncioResponseDto> editarAnuncio(
            @Parameter(
                    description = "ID del anuncio a editar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO con los datos para editar el anuncio",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EditarAnuncioRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "titulo": "Producto de Prueba2",
                                                        "descripcion": "Una descripción detallada del producto",
                                                        "precio": "25.5",
                                                        "imagen": "producto.jpg",
                                                        "tipoOperacion": "VENTA",
                                                        "condicion": "0",
                                                        "categoriaId": "",
                                                        "usuarioId":"6ac890d0-8ee2-4967-8bb7-cfa8b84376bc"
                                                    }
                                                    """
                                    )
                            }

                    )
            )
            @Valid @RequestBody EditarAnuncioRequestDto dto
    ){
        return ResponseEntity.status(HttpStatus.OK).body(AnuncioResponseDto.of(
            servicio.editarAnuncio(id, dto.toAnuncio(),
                    dto.usuarioId() // Cuando haya seguridad se deberá obtener el ID del usuario autenticado en lugar de recibirlo en el DTO
            )
        ));
    }

}
