package com.salesianostriana.dam.campusswap.seguridad.auth;

import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginRequest;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.LoginResponse;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.RegisterRequest;
import com.salesianostriana.dam.campusswap.seguridad.auth.dto.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticación",
        description = "Endpoints para el manejo de autenticación y registro de usuarios."
)
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario con sus credenciales y devuelve un token JWT de acceso."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso. Se devuelve el email y el token JWT.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de respuesta de login",
                                    value = """
                            {
                                "email": "antonio@campusswap.com",
                                "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbnRvbmlvQGNhbXB1c3N3YXAuY29tIiwiaWF0IjoxNzA5MDAwMDAwLCJleHAiOjE3MDkwODY0MDB9.xxxx"
                            }
                            """
                            )
                    )
            ),@ApiResponse(
                    responseCode = "401",
                    description = "Credenciales incorrectas. No se pudo autenticar al usuario.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de respuesta de error de login",
                                    value = """
                            {
                                "timestamp": "2024-06-01T12:00:00.000+00:00",
                                "status": 401,
                                "error": "Unauthorized",
                                "message": "Credenciales incorrectas.",
                                "path": "/auth/login"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida. El formato del request es incorrecto o faltan campos obligatorios.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de respuesta de solicitud inválida",
                                    value = """
                            {
                                "type": "about:blank",
                                "title": "Bad Request",
                                "status": 400,
                                "detail": "Error de validación en el cuerpo de la petición.",
                                "instance": "/auth/login",
                                "invalid-params": [
                                    {
                                        "name": "email",
                                        "reason": "must not be blank"
                                    },
                                    {
                                        "name": "password",
                                        "reason": "size must be between 8 and 64"
                                    }
                                ]
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Cuenta bloqueada. El usuario ha sido bloqueado por un administrador.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de respuesta de cuenta bloqueada",
                                    value = """
                            {
                                "timestamp": "2024-06-01T12:00:00.000+00:00",
                                "status": 403,
                                "error": "Forbidden",
                                "message": "Tu cuenta ha sido bloqueada por un administrador.",
                                "path": "/auth/login"
                            }
                            """
                            )
                    )
            )
    })
    public ResponseEntity<LoginResponse> doLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok()
                .body(authService.doLogin(loginRequest));

    }

    @PostMapping("/register")
    @Operation(
            summary = "Registrar usuario",
            description = "Crea una nueva cuenta de usuario en el sistema con los datos proporcionados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario registrado correctamente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class),
                            examples = @ExampleObject(
                                    name = "Registro exitoso",
                                    value = """
                                            {
                                                "uuid": "072be788-2bdf-4015-aa4a-5d3bd0958b0b",
                                                "nombre": "nombre prueba",
                                                "username": "usernameprueba",
                                                "email": "emailprueba@gmail.com",
                                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.xxx"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o email/username ya en uso.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Campos inválidos",
                                            value = """
                        {
                            "detail": "Fallo de validacion en uno o mas argumentos",
                            "instance": "/auth/register",
                            "status": 400,
                            "title": "Error de validación",
                            "type": "about:blank",
                            "invalid-params": [
                                {
                                    "objeto": "registerRequest",
                                    "mensaje": "El email es obligatorio",
                                    "campo": "email",
                                    "valorRechazado": ""
                                }
                            ]
                        }
                        """
                                    ),
                                    @ExampleObject(
                                            name = "Email o username duplicado",
                                            value = """
                        {
                            "detail": "Fallo de validacion en uno o mas argumentos",
                            "instance": "/auth/register",
                            "status": 400,
                            "title": "Error de validación",
                            "type": "about:blank",
                            "invalid-params": [
                                {
                                    "objeto": "registerRequest",
                                    "mensaje": "El email ya está registrado",
                                    "campo": "email",
                                    "valorRechazado": "emailprueba@gmail.com"
                                }
                            ]
                        }
                        """
                                    )
                            }
                    )
            )
    })
    public ResponseEntity<RegisterResponse> doRegister(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.doRegister(registerRequest));

    }
}
