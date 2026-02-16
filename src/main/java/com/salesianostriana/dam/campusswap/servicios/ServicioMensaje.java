package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioMensaje;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
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

    private final RepositorioMensaje repositorioMensaje;
    private final RepositorioAnuncio repositorioAnuncio;
    private final RepositorioUsuario repositorioUsuario;

    public Mensaje enviarMensaje(Mensaje mensaje) {
        Usuario emisor = repositorioUsuario.findById(mensaje.getEmisor().getId()).orElseThrow(() -> new NoSuchElementException("Emisor no encontrado"));
        Usuario receptor = repositorioUsuario.findById(mensaje.getReceptor().getId()).orElseThrow(() -> new NoSuchElementException("Receptor no encontrado"));
        Anuncio anuncio = repositorioAnuncio.findById(mensaje.getAnuncio().getId()).orElseThrow(() -> new NoSuchElementException("Anuncio no encontrado"));

        mensaje.setEmisor(emisor);
        mensaje.setReceptor(receptor);
        mensaje.setAnuncio(anuncio);

        return repositorioMensaje.save(mensaje);
    }

    public Page<Mensaje> obtenerMensajes(Long idAnuncio, Pageable pageable) {
        if (!repositorioAnuncio.existsById(idAnuncio))
            throw new NoSuchElementException("No se ha encontrado el anuncio con id: " + idAnuncio);
        return repositorioMensaje.findAllByAnuncioId(idAnuncio, pageable);
    }
}
