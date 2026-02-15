package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.servicios.ServicioFavorito;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favoritos")
@Tag(
        name = "Controlador Favorito",
        description = "Endpoints relacionados con la gestión de favoritos"
)
public class ControladorFavorito {

    private final ServicioFavorito servicioFavorito;

    @DeleteMapping("/{id}")
    @ApiResponse(
            responseCode = "204",
            description = "No Content - Favorito eliminado correctamente"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Forbidden - El usuario no es el propietario del favorito",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "No puedes eliminar un favorito que no es tuyo",
                                                "instance": "/api/v1/favoritos/1",
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
            description = "Internal Server Error - Error al eliminar el favorito",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "detail": "Ha ocurrido un error inesperado",
                                                "instance": "/api/v1/favoritos/1",
                                                "status": 500,
                                                "title": "Error inesperado."
                                            }
                                            """
                            )
                    }
            )
    )
    @Operation(
            summary = "Eliminar un favorito",
            description = "Elimina un favorito específico. Solo el usuario que creó el favorito puede eliminarlo."
    )
    public ResponseEntity<?> eliminarFavorito(
            @Parameter(
                    description = "ID del favorito a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @RequestBody String idUsuario
    ) {
        servicioFavorito.eliminarFavorito(id, idUsuario);
        return ResponseEntity.noContent().build();
    }


}
