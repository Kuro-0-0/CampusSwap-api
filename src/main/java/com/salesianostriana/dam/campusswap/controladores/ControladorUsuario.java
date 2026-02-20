package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.usuario.UsuarioResponseDto;
import com.salesianostriana.dam.campusswap.ficheros.general.utiles.MimeTypeDetector;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioUsuario;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.ValidImage;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @GetMapping()
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioLogueado(
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.ok(UsuarioResponseDto.of(servicioUsuario.obtenerDatosPerfil(usuario)));
    }



}
