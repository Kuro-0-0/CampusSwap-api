package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.Usuario;

import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseMensaje;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Log
@Service
@RequiredArgsConstructor
public class ServicioMensaje {

    private final ServicioBaseMensaje servicioBaseMensaje;
    private final ServicioBaseUsuario servicioBaseUsuario;
    private final ServicioBaseAnuncio servicioBaseAnuncio;

    public Mensaje enviarMensaje(Mensaje mensaje, Usuario usuario) {
        Usuario receptor = servicioBaseUsuario.buscarPorId(mensaje.getReceptor().getId());
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(mensaje.getAnuncio().getId());

        mensaje.setEmisor(usuario);
        mensaje.setReceptor(receptor);
        mensaje.setAnuncio(anuncio);

        return servicioBaseMensaje.guardar(mensaje);
    }

    public Page<Mensaje> obtenerMensajes(Long idAnuncio, Pageable pageable) {
        if (!servicioBaseAnuncio.existePorId(idAnuncio))
            throw new NoSuchElementException("No se ha encontrado el anuncio con id: " + idAnuncio);
        return servicioBaseMensaje.buscarTodosPorAnuncioId(idAnuncio, pageable);
    }



}
