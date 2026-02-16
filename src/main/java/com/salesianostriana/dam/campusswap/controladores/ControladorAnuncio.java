package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.BorrarAnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.servicios.ServicioAnuncio;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/anuncios")
@Tag(
        name = "Controlador para la gestion global de los anuncios",
        description = "Permite realizar las acciones especificadas sobre los anuncios."
)
public class ControladorAnuncio {

    private final ServicioAnuncio servicioAnuncio;

    @PostMapping
    @ApiResponse(
            responseCode = "201",
            description = "Anuncio creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnuncioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                            "id": 4,
                                                "titulo": "Laptop Dell XPS 15",
                                                "descripcion": "Laptop en excelente estado, poco uso, incluye cargador",
                                                "precio": null,
                                                "categoria": "Portátiles",
                                                "imagen": "https://ejemplo.com/laptop.jpg",
                                                "tipoOperacion": "INTERCAMBIO",
                                                "estado": "ACTIVO",
                                                "condicion": "COMO_NUEVO",
                                                "usuarioId": "a89f6904-4624-47bf-9967-be2e66aff7a2"
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
                                                "detail": "El precio debe ser nulo para cesión e intercambio, y mayor que 0 para venta",
                                                "instance": "/api/v1/anuncios",
                                                "status": 400,
                                                "title": "Solicitud inválida"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Entidad no encontrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado la entidad con id: X",
                                                "instance": "/api/v1/anuncios",
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
                                                "detail": "Ha ocurrido un error inesperado al procesar la solicitud",
                                                "instance": "/api/v1/anuncios",
                                                "status": 500,
                                                "title": "Error interno del servidor"
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Crear un nuevo anuncio",
            description = "Permite crear un nuevo anuncio en el sistema."
    )
    public ResponseEntity<AnuncioResponseDto> crearAnuncio(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DTO con los datos para crear el anuncio",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnuncioRequestDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "titulo": "Producto de Prueba2",
                                                "descripcion": "Una descripción detallada del producto",
                                                "precio": 25.5,
                                                "imagen": "producto.jpg",
                                                "tipoOperacion": "VENTA",
                                                "condicion": "NUEVO",
                                                "categoriaId": 1,
                                                "usuarioId":"6ac890d0-8ee2-4967-8bb7-cfa8b84376bc"
                                            }
                                            """
                            )
                    }

            )
    ) @RequestBody() AnuncioRequestDto dto) {
        Anuncio nuevoAnuncio = dto.toAnuncio();

        return ResponseEntity.status(HttpStatus.CREATED).body(AnuncioResponseDto.of(servicioAnuncio.crearAnuncio(nuevoAnuncio)));
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
            responseCode = "403",
            description = "Solicitud prohibida",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No puedes modificar un anuncio que no es tuyo",
                                                "instance": "/api/v1/anuncios/1",
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
            description = "Recurso no encontrado",
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
                                                "instance": "/api/v1/anuncios/1",
                                                "status": 500,
                                                "title": "Error inesperado."
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
                            schema = @Schema(implementation = AnuncioRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "titulo": "Producto de Prueba2",
                                                        "descripcion": "Una descripción detallada del producto",
                                                        "precio": 25.5,
                                                        "imagen": "producto.jpg",
                                                        "tipoOperacion": "VENTA",
                                                        "condicion": "NUEVO",
                                                        "categoriaId": 1,
                                                        "usuarioId":"6ac890d0-8ee2-4967-8bb7-cfa8b84376bc"
                                                    }
                                                    """
                                    )
                            }

                    )
            )
            @Valid @RequestBody AnuncioRequestDto dto
    ){
        return ResponseEntity.status(HttpStatus.OK).body(AnuncioResponseDto.of(
            servicioAnuncio.editarAnuncio(id, dto.toAnuncio(),
                    dto.usuarioId() // Cuando haya seguridad se deberá obtener el ID del usuario autenticado en lugar de recibirlo en el DTO
            )
        ));
    }


    @GetMapping("/{usuarioId}")
    @ApiResponse(
            responseCode = "200",
            description = "Anuncios obtenidos correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AnuncioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "titulo": "Portátil HP Victus 16GB RAM",
                                                        "descripcion": "Lo vendo porque me he comprado un Mac. Ideal para programar en IntelliJ.",
                                                        "precio": 650.0,
                                                        "categoria": "Electrónica e Informática",
                                                        "imagen": "hp_victus.jpg",
                                                        "tipoOperacion": "VENTA",
                                                        "estado": "ACTIVO",
                                                        "condicion": "COMO_NUEVO",
                                                        "usuarioId": "68b56db5-159a-4232-82d7-ef92c96368fa"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "titulo": "Bicicleta de montaña Rockrider",
                                                        "descripcion": "Cambio por un patinete eléctrico Xiaomi en buen estado.",
                                                        "precio": null,
                                                        "categoria": "Transporte",
                                                        "imagen": "bici.jpg",
                                                        "tipoOperacion": "INTERCAMBIO",
                                                        "estado": "ACTIVO",
                                                        "condicion": "USADO",
                                                        "usuarioId": "68b56db5-159a-4232-82d7-ef92c96368fa"
                                                    },
                                                    {
                                                        "id": 4,
                                                        "titulo": "Clean Code - Robert C. Martin",
                                                        "descripcion": "Libro imprescindible. Ya lo he leído.",
                                                        "precio": 20.0,
                                                        "categoria": "Libros y Apuntes",
                                                        "imagen": "cleancode.jpg",
                                                        "tipoOperacion": "VENTA",
                                                        "estado": "CERRADO",
                                                        "condicion": "NUEVO",
                                                        "usuarioId": "68b56db5-159a-4232-82d7-ef92c96368fa"
                                                    }
                                                ],
                                                "empty": false,
                                                "first": true,
                                                "last": true,
                                                "number": 0,
                                                "numberOfElements": 3,
                                                "pageable": {
                                                    "offset": 0,
                                                    "pageNumber": 0,
                                                    "pageSize": 20,
                                                    "paged": true,
                                                    "sort": {
                                                        "empty": true,
                                                        "sorted": false,
                                                        "unsorted": true
                                                    },
                                                    "unpaged": false
                                                },
                                                "size": 20,
                                                "sort": {
                                                    "empty": true,
                                                    "sorted": false,
                                                    "unsorted": true
                                                },
                                                "totalElements": 3,
                                                "totalPages": 1
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
                                                "detail": "No se ha encontrado el usuario con id: 123e4567-e89b-12d3-a456-426614174000",
                                                "instance": "/api/v1/anuncios/123e4567-e89b-12d3-a456-426614174000",
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
                                                "instance": "/api/v1/anuncios/1",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Obtener anuncios de un usuario",
            description = "Permite obtener una lista paginada de los anuncios publicados por un usuario específico."
    )
    public ResponseEntity<Page<AnuncioResponseDto>> obtenerAnuncios(@PathVariable String usuarioId, Pageable pageable) {
        return ResponseEntity.ok(servicioAnuncio.obtenerAnuncios(pageable,usuarioId).map(AnuncioResponseDto::of));
    }

    @Operation(
            summary = "Alternar el estado de un anuncio",
            description = "Permite alternar el estado de un anuncio entre ACTIVO y CERRADO. " +
                    "Solo el propietario del anuncio puede alternar su estado, y el anuncio no puede estar pausado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Estado del anuncio alternado correctamente",
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
                                                "estado": "CERRADO",
                                                "condicion": "NUEVO",
                                                "usuarioId": "6e44a229-0400-4903-9f58-11c63a1dc31a"
                                            }
                                            """
                            )
                    }
            )
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
                                                "detail": "No puedes modificar un anuncio que no es tuyo",
                                                "instance": "/api/v1/anuncios/2/alternar-estado",
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
            description = "Recurso no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se ha encontrado el usuario con id: 1",
                                                "instance": "/api/v1/anuncios/1/alternar-estado",
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
            description = "Estado inválido para alternar",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No se pueden modificar anuncios con estado: PAUSADO",
                                                "instance": "/api/v1/anuncios/1/alternar-estado",
                                                "status": 409,
                                                "title": "Solicitud inválida"
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
                                                "instance": "/api/v1/anuncios/1/alternar-estado",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    @PutMapping("/{id}/alternar-estado")
    public ResponseEntity<AnuncioResponseDto> alternarEstado(
            @Parameter(
                    description = "ID del anuncio al que se le quiere alternar el estado",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ID del usuario propietario del anuncio (en futuras implementaciones se obtendrá del token de autenticación)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string"),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    "6ac890d0-8ee2-4967-8bb7-cfa8b84376bc"
                                                    """
                                    )
                            }
                    )
            )
            @RequestBody String usuarioId // Cuando haya seguridad se deberá obtener el ID del usuario autenticado en lugar de recibirlo en el cuerpo de la petición
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                AnuncioResponseDto.of(
                        servicioAnuncio.alternarEstado(id, usuarioId)
                )
        );
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un anuncio",
            description = "Permite eliminar un anuncio existente. " +
                    "Solo el propietario del anuncio puede eliminarlo."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Anuncio eliminado correctamente",
            content = @Content(
                    mediaType = "application/json"
            )
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
                                                "detail": "No se puede eliminar un anuncio que no te pertenece",
                                                "instance": "/api/v1/anuncios/2",
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
            description = "Recurso no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                 "detail": "No se ha encontrado el anuncio con id: 20",
                                                 "instance": "/api/v1/anuncios/20",
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
                                                "instance": "/api/v1/anuncios/1",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<?> eliminarAnuncio(@RequestBody BorrarAnuncioRequestDto dto ,
                                             @Parameter(
                                                     description = "ID del anuncio a eliminar",
                                                     example = "1",
                                                     required = true
                                             )
                                             @PathVariable Long id){
        servicioAnuncio.borrarAnuncio(id,dto.usuarioId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
