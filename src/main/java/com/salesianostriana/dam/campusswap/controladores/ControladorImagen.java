package com.salesianostriana.dam.campusswap.controladores;

import com.salesianostriana.dam.campusswap.ficheros.general.utiles.MimeTypeDetector;
import com.salesianostriana.dam.campusswap.servicios.funciones.ServicioImagen;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Resource> obtenerFotoPerfil(@PathVariable String imageUrl) {

        Resource imagen = servicioImagen.obtenerImagen(imageUrl);
        String mimeType = mimeTypeDetector.getMimeType(imagen);

        return ResponseEntity.ok()
                .header("Content-Type", mimeType)
                .body(imagen);
    }
}
