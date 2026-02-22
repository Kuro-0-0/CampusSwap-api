package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.usuario.UsuarioResponseDto;
import com.salesianostriana.dam.campusswap.ficheros.general.utiles.MimeTypeDetector;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioUsuario;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.ValidImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@Tag(
        name = "Controlador para la gestion de datos de los usuarios",
        description = "Permite realizar las acciones especificadas sobre los usuarios."
)
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;
    private final MimeTypeDetector mimeTypeDetector;

    @PutMapping("/foto-perfil")
    @PreAuthorize("isAuthenticated()")
    @ApiResponse(
            responseCode = "200",
            description = "Foto de perfil actualizada correctamente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UsuarioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta al actualizar la foto de perfil",
                                    value = """
                                            {
                                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                                "nombre": "Juan Pérez",
                                                "email": "",
                                                "fotoPerfil": "foto_perfil_123e4567-e89b-12d3-a456-426614174000.jpg",
                                                "descripcion": "Amante de los libros y la tecnología.",
                                                "reputacionMedia": 4.5,
                                                "fechaRegistro": "2023-01-15T10:30:00",
                                                "username": "juanperez",
                                                "roles": [
                                                    "USER"
                                                ]
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "No autorizado. El usuario no ha iniciado sesión o el token es inválido.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta 401 al actualizar la foto de perfil",
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00",
                                                "status": 401,
                                                "error": "Unauthorized",
                                                "message": "No se ha proporcionado un token de autenticación válido.",
                                                "path": "/api/v1/usuarios/foto-perfil"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Prohibido. El usuario no tiene permisos para realizar esta acción.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta 403 al actualizar la foto de perfil",
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00",
                                                "status": 403,
                                                "error": "Forbidden",
                                                "message": "No tienes permisos para actualizar la foto de perfil.",
                                                "path": "/api/v1/usuarios/foto-perfil"
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor. Ocurrió un error al procesar la solicitud.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta 500 al actualizar la foto de perfil",
                                    value = """
                                            {
                                                "timestamp": "2024-06-01T12:00:00",
                                                "status": 500,
                                                "error": "Internal Server Error",
                                                "message": "Ocurrió un error al procesar la solicitud.",
                                                "path": "/api/v1/usuarios/foto-perfil"
                                            }
                                            """
                            )
                    }
            )
    )
    public ResponseEntity<UsuarioResponseDto> actualizarFotoPerfil(
            @AuthenticationPrincipal Usuario usuario,
            @ValidImage @RequestPart("file") MultipartFile file
    ){
        return ResponseEntity.ok(UsuarioResponseDto.of(servicioUsuario.actualizarFotoPerfil(usuario, file)));
    }


    @ApiResponse(
            responseCode = "200",
            description = "Usuario obtenido correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": "a2067420-127d-4486-a266-33c9e4408d09",
                                                "nombre": "Carlos Vendedor",
                                                "email": "carlos@salesianos.edu",
                                                "reputacionMedia": 5.0,
                                                "imageUrl": null,
                                                "fechaRegistro": "2026-02-20 14:20:27",
                                                "roles": [
                                                    "USUARIO"
                                                ]
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
            summary = "Obtener los datos el usuario logueado",
            description = "Permite obtener todos los datos del usuario logueado que emita la petición."
    )
    @GetMapping()
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioLogueado(
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.ok(UsuarioResponseDto.of(servicioUsuario.obtenerDatosPerfil(usuario)));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener los datos de un usuario por su ID",
            description = "Permite obtener todos los datos de un usuario específico utilizando su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuario obtenido correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDto.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": "a2067420-127d-4486-a266-33c9e4408d09",
                                                "nombre": "Carlos Vendedor",
                                                "email": "carlos@salesianos.edu",
                                                "reputacionMedia": 5.0,
                                                "imageUrl": null,
                                                "fechaRegistro": "2026-02-20 14:20:27",
                                                "roles": [
                                                    "USUARIO"
                                                ]
                                            }
                                            """                            )
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
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorId(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(UsuarioResponseDto.of(servicioUsuario.obtenerDatosPerfil(id)));
    }



}
