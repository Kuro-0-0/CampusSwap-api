package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.categoria.CategoriaResponseDto;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.usuario.UsuarioResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioAdministrador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ControladorAdministrador {

    private final ServicioAdministrador servicioAdministrador;

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


    @PatchMapping("/usuarios/{id}/bloquear")
    public ResponseEntity<UsuarioResponseDto> bloquearUsuario(
            @PathVariable UUID id
            ){
        return ResponseEntity.ok(
                UsuarioResponseDto.of(
                        servicioAdministrador.bloquearUsuario(id)
                )
        );
    }
}
