package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final RepositorioAnuncio repositorioAnuncio;
    private final RepositorioUsuario repositorioUsuario;
    public Anuncio crearAnuncio(Anuncio anuncio) {
        Usuario usuario = repositorioUsuario.findById(anuncio.getUsuario().getId()).orElseThrow(() -> new NoSuchElementException("Usuario con ID " + anuncio.getUsuario().getId() + " no encontrado"));

        if((anuncio.getTipoOperacion().equals(TipoOperacion.CESION) || anuncio.getTipoOperacion().equals(TipoOperacion.INTERCAMBIO)) && anuncio.getPrecio() != null) {
            throw new IllegalArgumentException("Un anuncio de cesión o intercambio no puede tener un precio establecido");
        }

        anuncio.setUsuario(usuario);
        anuncio.setEstado(Estado.ACTIVO);
        usuario.agregarAnuncio(anuncio);
        return repositorioAnuncio.save(anuncio);
    }


}
