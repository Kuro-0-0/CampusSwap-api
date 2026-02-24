package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.ficheros.logica.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioImagen {

    private final StorageService storageService;

    public Resource obtenerImagen(String imageUrl) {
        return storageService.loadAsResource(imageUrl);
    }

    public void eliminarImagen(String imageUrl) {
        storageService.deleteFile(imageUrl);
    }
}
