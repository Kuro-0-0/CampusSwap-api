package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.errores.custom.NonExistentImageException;
import com.salesianostriana.dam.campusswap.ficheros.general.model.FileMetadata;
import com.salesianostriana.dam.campusswap.ficheros.logica.StorageService;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class ServicioUsuario {

    private final ServicioBaseUsuario servicioBaseUsuario;
    private final StorageService storageService;
    private final ServicioValoracion servicioValoracion;

    public Usuario actualizarFotoPerfil(Usuario usuarioAutenticado, MultipartFile file) {

        Usuario usuario = servicioBaseUsuario.buscarPorId(usuarioAutenticado.getId());

        if(usuario.getFotoPerfil() !=null){
            storageService.deleteFile(usuario.getFotoPerfil());
        }

        FileMetadata fileMetadata = storageService.store(file);

        usuario.setFotoPerfil(fileMetadata.getFilename());

        return servicioBaseUsuario.guardar(usuario);

    }


    public Usuario obtenerDatosPerfil(Usuario usuario) {
        servicioValoracion.calcularMediaValoraciones(usuario);
        return usuario;
    }

    public Resource obtenerFotoPerfil(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new NonExistentImageException("El usuario no tiene una foto de perfil asignada");
        }
        return storageService.loadAsResource(imageUrl);
    }

}
