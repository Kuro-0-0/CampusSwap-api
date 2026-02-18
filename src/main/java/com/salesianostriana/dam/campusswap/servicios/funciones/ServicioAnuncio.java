package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.Reporte;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.errores.custom.NotOwnedException;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseCategoria;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseReporte;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log
@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final ServicioBaseUsuario servicioBaseUsuario;
    private final ServicioBaseCategoria servicioBaseCategoria;
    private final ServicioBaseAnuncio servicioBaseAnuncio;
    private final ServicioBaseReporte servicioBaseReporte;

    public Anuncio crearAnuncio(Anuncio anuncio, Usuario usuario) {
        Categoria categoria = servicioBaseCategoria.buscarPorId(anuncio.getCategoria().getId());

        anuncio.setEstado(Estado.ACTIVO);
        anuncio.setUsuario(usuario);
        anuncio.setCategoria(categoria);
        return servicioBaseAnuncio.guardar(anuncio);
    }

    public Anuncio editarAnuncio(Long id, Anuncio anuncio, Usuario usuario) {
        Anuncio original = servicioBaseAnuncio.buscarPorId(id);
        Categoria categoria = servicioBaseCategoria.buscarPorId(anuncio.getCategoria().getId());

        if (original.getUsuario() == null || !original.getUsuario().equals(usuario))
            throw new NotOwnedException("No puedes modificar un anuncio que no es tuyo");

        if (original.getEstado().equals(Estado.CERRADO))
            throw new IllegalStateException("No se pueden modificar anuncios cerrados");

        return servicioBaseAnuncio.guardar(original.modificar(anuncio));
    }

    public Page<Anuncio> obtenerAnuncios(Pageable pageable, String idUsuario) {
        Usuario usuario = servicioBaseUsuario.buscarPorId(idUsuario);
        return servicioBaseAnuncio.findByUsuarioId(UUID.fromString(idUsuario), pageable);
    }


    public Anuncio alternarEstado(Long id, String usuarioId) {
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(id);
        Usuario usuario = servicioBaseUsuario.buscarPorId(usuarioId);

        if (anuncio.getUsuario() == null || !anuncio.getUsuario().equals(usuario))
            throw new NotOwnedException("No puedes modificar un anuncio que no es tuyo");

        if (anuncio.getEstado().equals(Estado.CERRADO))
            anuncio.setEstado(Estado.ACTIVO);
        else if (anuncio.getEstado().equals(Estado.ACTIVO))
            anuncio.setEstado(Estado.CERRADO);
        else
            throw new IllegalStateException("No se pueden modificar anuncios con estado: " + anuncio.getEstado());

        return servicioBaseAnuncio.guardar(anuncio);
    }


    public void borrarAnuncio(Long id, String idUsuario) {

        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(id);
        Usuario usuario = servicioBaseUsuario.buscarPorId(idUsuario);


        if(!anuncio.getUsuario().equals(usuario)){
            throw new NotOwnedException("No se puede eliminar un anuncio que no te pertenece");
        }

        servicioBaseAnuncio.borrar(anuncio);
    }

    public Reporte reportarAnuncio(Long anuncioId, Reporte reporte) {
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(anuncioId);
        Usuario usuario = servicioBaseUsuario.buscarPorId(reporte.getUsuario().getId());

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

}
