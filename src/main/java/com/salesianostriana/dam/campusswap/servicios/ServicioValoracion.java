package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioValoracion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioValoracion {


    private final RepositorioValoracion repositorioValoracion;
    private final RepositorioUsuario  repositorioUsuario;
    private final RepositorioAnuncio repositorioAnuncio;

    public Double calcularMediaValoraciones(String usuarioId) {

        Usuario usuario = repositorioUsuario.findById(UUID.fromString(usuarioId))
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario con ID: " + usuarioId));

        Double media = repositorioValoracion.calcularMediaValoracionesUsuario(usuario.getId());

        if(media == null)
            throw  new  IllegalStateException("El usuario no tiene valoraciones");

        usuario.setReputacionMedia(media);
        repositorioUsuario.save(usuario);
        return media;

    }

    public Valoracion crearValoracion(Valoracion valoracion) {
        Anuncio anuncio = repositorioAnuncio.findById(valoracion.getAnuncio().getId())
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el anuncio con ID: " + valoracion.getAnuncio().getId()));

        if (repositorioValoracion.existsByAnuncioId(valoracion.getAnuncio().getId()))
            throw new IllegalStateException("Este anuncio ya ha sido valorado");

        if (!anuncio.getEstado().equals(Estado.CERRADO))
            throw new IllegalStateException("Solo se pueden valorar anuncios cerrados");

        Usuario evaluado = repositorioUsuario.findById(anuncio.getUsuario().getId())
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario evaluado con ID: " + valoracion.getEvaluado().getId()));

        Usuario evaluador = repositorioUsuario.findById(valoracion.getEvaluador().getId())
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el usuario evaluador con ID: " + valoracion.getEvaluador().getId()));

        if (evaluado.equals(evaluador))
            throw new IllegalStateException("No puedes valorarte a ti mismo");

        valoracion.setAnuncio(anuncio);
        valoracion.setEvaluado(evaluado);
        valoracion.setEvaluador(evaluador);
        Valoracion v =  repositorioValoracion.save(valoracion);
        this.calcularMediaValoraciones(evaluado.getId().toString());
        return v;
    }
}
