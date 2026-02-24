package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaRequestDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaRequestUpdateDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.usuario.UsuarioResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioAdministrador;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(
        name = "Administrador",
        description = "Operaciones de administración para gestionar categorías"
)
public class ControladorAdministrador {

    private final ServicioAdministrador servicioAdministrador;

    @PostMapping("/categorias")
    @ApiResponse(
            responseCode = "201",
            description = "Categoría creada exitosamente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CategoriaResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "id": 1,
                                            "nombre": "Libros",
                                            "descripcion": "Categoría para libros de texto y literatura"
                                        }
                                        """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida, por ejemplo, si el nombre de la categoría ya existe o si faltan campos obligatorios",

            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "timestamp": "2024-06-01T12:00:00Z",
                                            "status": 400,
                                            "error": "Bad Request",
                                            "message": "El nombre de la categoría ya existe",
                                            "path": "/api/v1/admin/categorias"
                                        }
                                        """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "timestamp": "2024-06-01T12:00:00Z",
                                            "status": 401,
                                            "error": "Unauthorized",
                                            "message": "No autorizado, se requieren permisos de administrador",
                                            "path": "/api/v1/admin/categorias"
                                        }
                                        """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "timestamp": "2024-06-01T12:00:00Z",
                                            "status": 403,
                                            "error": "Forbidden",
                                            "message": "Prohibido, se requieren permisos de administrador",
                                            "path": "/api/v1/admin/categorias"
                                        }
                                        """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "timestamp": "2024-06-01T12:00:00Z",
                                            "status": 500,
                                            "error": "Internal Server Error",
                                            "message": "Ocurrió un error inesperado al procesar la solicitud",
                                            "path": "/api/v1/admin/categorias"
                                        }
                                        """
                            )
                    }
            )
    )
    @Operation(
            summary = "Crear una nueva categoría",
            description = "Permite a un administrador crear una nueva categoría para clasificar los anuncios."
    )
    public ResponseEntity<CategoriaResponseDto> crearCategoria(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nueva categoría",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CategoriaRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                                    {
                                                        "nombre": "Libros",
                                                        "descripcion": "Categoría para libros de texto y literatura"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @Valid @RequestBody CategoriaRequestDto categoriaRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CategoriaResponseDto.of(
                        servicioAdministrador.crearCategoria(categoriaRequestDto.toCategoria())
                )
        );
    }

    @GetMapping("/categorias/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Categoría obtenida exitosamente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CategoriaResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "id": 1,
                                            "nombre": "Libros",
                                            "descripcion": "Categoría para libros de texto y literatura"
                                        }
                                        """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 401,
                                                "error": "Unauthorized",
                                                "message": "No autorizado, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 403,
                                                "error": "Forbidden",
                                                "message": "Prohibido, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada, el ID proporcionado no corresponde a ninguna categoría existente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "timestamp": "2024-06-01T12:00:00Z",
                                            "status": 404,
                                            "error": "Not Found",
                                            "message": "Categoría con ID 1 no encontrada",
                                            "path": "/api/v1/admin/categorias/1"
                                        }
                                        """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                        {
                                            "timestamp": "2024-06-01T12:00:00Z",
                                            "status": 500,
                                            "error": "Internal Server Error",
                                            "message": "Ocurrió un error inesperado al procesar la solicitud",
                                            "path": "/api/v1/admin/categorias/1"
                                        }
                                        """
                            )
                    }
            )
    )
    @Operation(
            summary = "Obtener una categoría por ID",
            description = "Permite a un administrador obtener los detalles de una categoría específica utilizando su ID."
    )
    public ResponseEntity<CategoriaResponseDto> obtenerCategoria(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(
                CategoriaResponseDto.of(
                        servicioAdministrador.obtenerCategoria(id)
                )
        );
    }

    @PutMapping("/categorias/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Categoría actualizada exitosamente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CategoriaResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "nombre": "Libros Actualizada",
                                                "descripcion": "Categoría para libros de texto y literatura actualizada"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                            "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 401,
                                                "error": "Unauthorized",
                                                "message": "No autorizado, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 403,
                                                "error": "Forbidden",
                                                "message": "Prohibido, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada, el ID proporcionado no corresponde a ninguna categoría existente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 404,
                                                "error": "Not Found",
                                                "message": "Categoría con ID 1 no encontrada",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 500,
                                                "error": "Internal Server Error",
                                                "message": "Ocurrió un error inesperado al procesar la solicitud",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Actualizar una categoría existente",
            description = "Permite a un administrador actualizar los detalles de una categoría específica utilizando su ID."
    )
    public ResponseEntity<CategoriaResponseDto> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestUpdateDto categoriaRequestDto
    ) {
        return ResponseEntity.ok().body(
                CategoriaResponseDto.of(
                        servicioAdministrador.actualizarCategoria(id, categoriaRequestDto.toCategoria())
                )
        );
    }

    @DeleteMapping("/categorias/{id}")
    @ApiResponse(
            responseCode = "204",
            description = "Categoría eliminada exitosamente, no se devuelve contenido en la respuesta",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 401,
                                                "error": "Unauthorized",
                                                "message": "No autorizado, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 403,
                                                "error": "Forbidden",
                                                "message": "Prohibido, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 500,
                                                "error": "Internal Server Error",
                                                "message": "Ocurrió un error inesperado al procesar la solicitud",
                                                "path": "/api/v1/admin/categorias/1"
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Eliminar una categoría por ID",
            description = "Permite a un administrador eliminar una categoría específica utilizando su ID. Esta operación no devuelve contenido en la respuesta."
    )
    public ResponseEntity<Void> eliminarCategoria(
            @PathVariable Long id
    ) {
        servicioAdministrador.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de categorías obtenida exitosamente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "nombre": "Libros",
                                                        "descripcion": "Categoría para libros de texto y literatura"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "nombre": "Electrónica",
                                                        "descripcion": "Categoría para dispositivos electrónicos"
                                                    }
                                                ],
                                                "pageable": {
                                                    "sort": {
                                                        "sorted": false,
                                                        "unsorted": true,
                                                        "empty": true
                                                    },
                                                    "pageNumber": 0,
                                                    "pageSize": 20,
                                                    "offset": 0,
                                                    "paged": true,
                                                    "unpaged": false
                                                },
                                                "totalPages": 1,
                                                "totalElements": 2,
                                                "last": true,
                                                "size": 20,
                                                "number": 0,
                                                "sort": {
                                                    "sorted": false,
                                                    "unsorted": true,
                                                    "empty": true
                                                },
                                                "numberOfElements": 2,
                                                "first": true,
                                                "empty": false
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 401,
                                                "error": "Unauthorized",
                                                "message": "No autorizado, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido, el usuario no tiene permisos de administrador",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 403,
                                                "error": "Forbidden",
                                                "message": "Prohibido, se requieren permisos de administrador",
                                                "path": "/api/v1/admin/categorias"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00Z",
                                                "status": 500,
                                                "error": "Internal Server Error",
                                                "message": "Ocurrió un error inesperado al procesar la solicitud",
                                                "path": "/api/v1/admin/categorias"
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Listar categorías con paginación y filtrado opcional por nombre",
            description = """
                    Permite a un administrador obtener una lista paginada de categorías, con la opción de filtrar por nombre. 
                    Si se proporciona el parámetro 'nombre', se devolverán solo las categorías cuyo nombre contenga el valor especificado (sin distinguir mayúsculas/minúsculas). 
                    Si no se proporciona el parámetro 'nombre', se devolverán todas las categorías paginadas.
            """
    )
    public ResponseEntity<Page<CategoriaResponseDto>> listarCategorias(
            @RequestParam(required = false) String nombre,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(
                servicioAdministrador.listarCategorias(nombre,pageable).map(CategoriaResponseDto::of)
        );
    }

    @GetMapping("/usuarios")
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Permite obtener una lista paginada de todos los usuarios registrados en el sistema. Este endpoint es de uso exclusivo para administradores."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuarios obtenidos correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": "a89f6904-4624-47bf-9967-be2e66aff7a2",
                                                        "nombre": "Admin CampusSwap",
                                                        "email": "admin@campusswap.com",
                                                        "reputacionMedia": 5.0,
                                                        "imageUrl": "https://ejemplo.com/admin.jpg",
                                                        "fechaRegistro": "2023-10-15T10:30:00",
                                                        "roles": ["USUARIO", "ADMIN"]
                                                    },
                                                    {
                                                        "id": "68b56db5-159a-4232-82d7-ef92c96368fa",
                                                        "nombre": "Juan Pérez",
                                                        "email": "juan@campusswap.com",
                                                        "reputacionMedia": 4.2,
                                                        "imageUrl": "https://ejemplo.com/juan.jpg",
                                                        "fechaRegistro": "2023-11-01T12:00:00",
                                                        "roles": ["USUARIO"]
                                                    }
                                                ],
                                                "empty": false,
                                                "first": true,
                                                "last": true,
                                                "number": 0,
                                                "numberOfElements": 2,
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
                                                "totalElements": 2,
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
                                                "instance": "/api/v1/admin/usuarios",
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
                                                "detail": "Acceso denegado. No tienes permisos de administrador para acceder a este recurso.",
                                                "instance": "/api/v1/admin/usuarios",
                                                "status": 403,
                                                "title": "Recurso no perteneciente al usuario"
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
                                                "instance": "/api/v1/admin/usuarios",
                                                "status": 500,
                                                "title": "Error interno del servidor"
                                            }
                                            """
                            )
                    }
            )
    )
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    public ResponseEntity<Page<UsuarioResponseDto>> listarUsuarios(
            @Parameter(description = "Configuración de paginación (ej. ?page=0&size=10)") Pageable pageable) {
        return ResponseEntity.ok(servicioAdministrador.listarUsuarios(pageable).map(UsuarioResponseDto::of));
    }

    @DeleteMapping("/anuncios/{id}")
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Anuncio eliminado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Void.class)
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado, el usuario no tiene permisos de administrador",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "timestamp": "2024-06-01T12:00:00Z",
                                        "status": 401,
                                        "error": "Unauthorized",
                                        "message": "No autorizado, se requieren permisos de administrador",
                                        "path": "/api/v1/admin/anuncios/1"
                                    }
                                    """                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido, el usuario no tiene permisos de administrador",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "timestamp": "2024-06-01T12:00:00Z",
                                        "status": 403,
                                        "error": "Forbidden",
                                        "message": "Prohibido, se requieren permisos de administrador",
                                        "path": "/api/v1/admin/anuncios/1"
                                    }
                                    """                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Anuncio no encontrado, el ID proporcionado no corresponde a ningún anuncio existente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "timestamp": "2024-06-01T12:00:00Z",
                                        "status": 404,
                                        "error": "Not Found",
                                        "message": "Anuncio con ID 1 no encontrado",
                                        "path": "/api/v1/admin/anuncios/1"
                                    }
                                    """                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "timestamp": "2024-06-01T12:00:00Z",
                                        "status": 500,
                                        "error": "Internal Server Error",
                                        "message": "Ocurrió un error inesperado al procesar la solicitud",
                                        "path": "/api/v1/admin/anuncios/1"
                                    }
                                    """                    )
            )
    )
    @Operation(
            summary = "Eliminar un anuncio por ID",
            description = "Permite a un administrador eliminar un anuncio específico utilizando su ID."
    )
    public ResponseEntity<Void> eliminarAnuncio(
            @PathVariable Long id
    ) {
        servicioAdministrador.eliminarAnuncio(id);
        return ResponseEntity.noContent().build();
    }
}
