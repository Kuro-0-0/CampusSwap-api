package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

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


    public void borrarAnuncio(Long id, String idUsuario) {

        Anuncio anuncio = repositorioAnuncio.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("No se ha encontrado el anuncio")
                );

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(idUsuario))
                .orElseThrow(()-> new IllegalArgumentException("No se ha encontrado el usuario")
                );


        if(!anuncio.getUsuario().equals(usuario)){
            throw new IllegalArgumentException("No se puede eliminar un anuncio que no te pertenece");
        }

        usuario.borrarAnuncio(anuncio);
        repositorioUsuario.save(usuario);
        repositorioAnuncio.delete(anuncio);


    }
}
