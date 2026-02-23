package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.ficheros.general.model.FileMetadata;
import com.salesianostriana.dam.campusswap.ficheros.logica.StorageService;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


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

    public Usuario buscarUsuarioPorId(String id) {
        return servicioBaseUsuario.buscarPorId(id);
    }

    public Usuario obtenerDatosPerfil(String id) {
        Usuario usuario = servicioBaseUsuario.buscarPorId(id);
        servicioValoracion.calcularMediaValoraciones(usuario);
        return usuario;
    }

    public Usuario obtenerDatosPerfil(Usuario usuario) {
        servicioValoracion.calcularMediaValoraciones(usuario);
        return usuario;
    }
}
