package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioFiltroDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioCatalogo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalogo")
@Tag(
        name = "Controlador de Catálogo",
        description = "Endpoints relacionados con la gestión del catálogo de anuncios."
)
public class ControladorCatalogo {

    private final ServicioCatalogo catalogoService;


    @GetMapping
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
                                                        "usuarioId": "19b4a774-e8cb-4b93-af65-ce67d0db5f85"
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
                                                        "usuarioId": "19b4a774-e8cb-4b93-af65-ce67d0db5f85"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "titulo": "Apuntes de Base de Datos 1º DAM",
                                                        "descripcion": "Regalo mis apuntes en limpio del año pasado. A quien venga a buscarlos.",
                                                        "precio": null,
                                                        "categoria": "Libros y Apuntes",
                                                        "imagen": "apuntes_sql.jpg",
                                                        "tipoOperacion": "CESION",
                                                        "estado": "ACTIVO",
                                                        "condicion": "USADO",
                                                        "usuarioId": "d89fef2e-7736-496c-9d75-2cc4510a5d1a"
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
            description = "Prohibido. El usuario autenticado no tiene permisos para acceder a este recurso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Acceso prohibido. El usuario autenticado no tiene permisos para acceder a este recurso.",
                                                "instance": "/api/v1/catalogo",
                                                "status": 403,
                                                "title": "Prohibido."
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
    @Operation(
            summary = "Obtener catálogo de anuncios activos",
            description = "Obtiene una página de anuncios, con opción de búsqueda por título o descripción y filtros opcionales por categoría, rango de precio, tipo de operación y estado."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<Page<AnuncioResponseDto>> obtenerCatalogo(@PageableDefault(page = 0, size = 10, sort = "fechaPublicacion",
                                                                            direction = Sort.Direction.DESC)Pageable pageable, @RequestParam(required = false) String q,
                                                                    @RequestParam(required = false) Long categoriaId,
                                                                    @RequestParam(required = false) Double minPrecio,
                                                                    @RequestParam(required = false) Double maxPrecio,
                                                                    @RequestParam(required = false) TipoOperacion tipoOperacion,
                                                                    @RequestParam(required = false) Estado estado,
                                                                    @AuthenticationPrincipal Usuario usuario
                                                                    ){

        AnuncioFiltroDto filtro = new AnuncioFiltroDto(q, categoriaId, minPrecio, maxPrecio, tipoOperacion, estado);

        return ResponseEntity.ok(catalogoService.obtenerCatalogo(pageable, filtro, usuario).map(AnuncioResponseDto::of));
    }

}
