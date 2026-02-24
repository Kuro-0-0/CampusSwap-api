package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.*;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.ficheros.general.model.FileMetadata;
import com.salesianostriana.dam.campusswap.ficheros.logica.StorageService;
import com.salesianostriana.dam.campusswap.servicios.base.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Log
@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final ServicioBaseUsuario servicioBaseUsuario;
    private final ServicioBaseCategoria servicioBaseCategoria;
    private final ServicioBaseAnuncio servicioBaseAnuncio;
    private final ServicioBaseReporte servicioBaseReporte;
    private final ServicioBaseFavorito servicioBaseFavorito;
    private final ServicioBaseMensaje servicioBaseMensaje;
    private final StorageService storageService;


    public Anuncio crearAnuncio(Anuncio anuncio, Usuario usuario, MultipartFile file) {
        Categoria categoria = servicioBaseCategoria.buscarPorId(anuncio.getCategoria().getId());
        FileMetadata fileMetadata = storageService.store(file);

        anuncio.setEstado(Estado.ACTIVO);
        anuncio.setUsuario(usuario);
        anuncio.setCategoria(categoria);
        anuncio.setImagen(fileMetadata.getFilename());

        System.out.println("Anuncio: " + anuncio.getTitulo() + " Usuario: " + usuario.getUsername() + " Categoria: " + categoria.getNombre() + " Imagen: " + anuncio.getImagen());

        return servicioBaseAnuncio.guardar(anuncio);
    }

    @Transactional
    public Anuncio editarAnuncio(Long id, Anuncio anuncio, Usuario usuario, MultipartFile file) {
        Anuncio original = servicioBaseAnuncio.buscarPorId(id);
        Categoria categoria = servicioBaseCategoria.buscarPorId(anuncio.getCategoria().getId());

        if (original.getUsuario() == null || !original.getUsuario().equals(usuario))
            throw new NotOwnedException("No puedes modificar un anuncio que no es tuyo");

        if (original.getEstado().equals(Estado.CERRADO))
            throw new IllegalStateException("No se pueden modificar anuncios cerrados");

        if (file != null) {
            String oldFilename = original.getImagen();
            FileMetadata fileMetadata = storageService.store(file);
            if (oldFilename != null && !oldFilename.isBlank()) {
                storageService.deleteFile(oldFilename);
            }
            original.setImagen(fileMetadata.getFilename());
        }

        return servicioBaseAnuncio.guardar(original.modificar(anuncio));
    }

    public Page<Anuncio> obtenerAnuncios(Pageable pageable, String idUsuario) {
        Usuario usuario = servicioBaseUsuario.buscarPorId(idUsuario);
        return servicioBaseAnuncio.findByUsuarioId(UUID.fromString(idUsuario), pageable);
    }


    @Transactional
    public Anuncio alternarEstado(Long id, Usuario usuario) {
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(id);

        if (anuncio.getUsuario() == null || !anuncio.getUsuario().equals(usuario))
            throw new NotOwnedException("No puedes modificar un anuncio que no es tuyo");

        if (anuncio.getEstado().equals(Estado.PAUSADO))
            anuncio.setEstado(Estado.ACTIVO);
        else if (anuncio.getEstado().equals(Estado.ACTIVO))
            anuncio.setEstado(Estado.PAUSADO);
        else
            throw new IllegalStateException("No se pueden modificar anuncios con estado: " + anuncio.getEstado());
        return servicioBaseAnuncio.guardar(anuncio);
    }


    public void borrarAnuncio(Long id) {
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(id);

        List<Favorito> favoritos = servicioBaseFavorito.buscarPorAnuncioId(anuncio.getId());

        if(!favoritos.isEmpty()){
            favoritos.forEach(servicioBaseFavorito::borrar);
        }

        Page<Mensaje> mensajes = servicioBaseMensaje.buscarTodosPorAnuncioId(anuncio.getId(), Pageable.unpaged());
        if(mensajes.hasContent()){
            mensajes.getContent().forEach(servicioBaseMensaje::borrar);
        }

        List<Reporte> reportes = servicioBaseReporte.BuscarPorAnuncioId(anuncio.getId());
        if(!reportes.isEmpty()){
            reportes.forEach(servicioBaseReporte::borrar);
        }

        servicioBaseAnuncio.borrar(anuncio);
    }

    public Reporte reportarAnuncio(Long anuncioId, Reporte reporte, Usuario usuario) {
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(anuncioId);

        if(servicioBaseReporte.buscarPorAnuncioIdYUsuarioId(anuncio.getId(), usuario.getId()).isPresent())
            throw new IllegalStateException("Ya has reportado este anuncio");

        if (anuncio.getEstado().equals(Estado.CERRADO)){
            throw new IllegalStateException("No se pueden reportar anuncios cerrados");
        }

        if (anuncio.getUsuario().equals(usuario)){
            throw new IllegalStateException("No se pueden reportar tus propios anuncios");
        }

        reporte.setAnuncio(anuncio);
        reporte.setUsuario(usuario);
        return servicioBaseReporte.guardar(reporte);
    }

    public Anuncio buscarPorId(Long id) {
        return servicioBaseAnuncio.buscarPorId(id);
    }
}
