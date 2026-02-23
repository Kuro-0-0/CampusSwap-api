package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.ficheros.general.utiles.MimeTypeDetector;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioImagen;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
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
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String imageUrl, @RequestParam(value = "v", required = false) String version) {

        String imagenARescatar = imageUrl;

        //Si se pasa versión
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
