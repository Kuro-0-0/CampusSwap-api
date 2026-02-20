package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.usuario.UsuarioResponseDto;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioUsuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping()
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioLogueado(
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.ok(UsuarioResponseDto.of(servicioUsuario.obtenerDatosPerfil(usuario)));
    }

}
