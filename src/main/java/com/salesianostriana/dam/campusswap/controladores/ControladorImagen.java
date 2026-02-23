package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.ficheros.general.utiles.MimeTypeDetector;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioImagen;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/imagen")
@Tag(
        name = "Controlador para la gestion de las imagenes",
        description = "Permite realizar las acciones especificadas sobre las imagenes."
)
public class ControladorImagen {

    private final ServicioImagen servicioImagen;
    private final MimeTypeDetector mimeTypeDetector;

    @GetMapping("/{imageUrl}")
    @Operation(
            summary = "Obtener una imagen",
            description = "Permite obtener una imagen a partir de su URL. Se puede especificar una versión de la imagen mediante el parámetro 'v' (t: thumbnail, m: mid, h: high)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Imagen obtenida correctamente",
            content = @Content(
                    mediaType = "application/octet-stream",
                    schema = @Schema(type = "string", format = "binary"),
                    examples = @ExampleObject(
                            value = "La imagen se devuelve como un flujo de bytes en la respuesta."
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "No se ha encontrado la imagen",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema( implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "timestamp": "2024-06-01T12:00:00",
                                        "status": 404,
                                        "error": "Not Found",
                                        "message": "No se ha encontrado la imagen con URL: {imageUrl}",
                                        "path": "/api/v1/imagen/{imageUrl}"
                                    }
                                    """
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema( implementation = ProblemDetail.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "timestamp": "2024-06-01T12:00:00",
                                        "status": 500,
                                        "error": "Internal Server Error",
                                        "message": "Ha ocurrido un error al procesar la solicitud.",
                                        "path": "/api/v1/imagen/{imageUrl}"
                                    }
                                    """
                    )
            )
    )
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String imageUrl, @RequestParam(value = "v", required = false) String version) {

        String imagenARescatar = imageUrl;

        if(version != null && !version.isBlank()){
            String extension = StringUtils.getFilenameExtension(imageUrl);

            String baseName = imageUrl.replace("." + extension, "");

            switch (version.toLowerCase()){
                case "t":
                    imagenARescatar = baseName + "_thumb." + extension;
                    break;
                case "m":
                    imagenARescatar = baseName + "_mid." + extension;
                    break;
                case "h":
                    imagenARescatar = baseName + "_high." + extension;
                    break;
            }
        }

        Resource imagen = servicioImagen.obtenerImagen(imagenARescatar);
        String mimeType = mimeTypeDetector.getMimeType(imagen);

        return ResponseEntity.ok()
                .header("Content-Type", mimeType)
                .body(imagen);
    }
}
