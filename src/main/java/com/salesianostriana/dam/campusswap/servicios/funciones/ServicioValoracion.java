package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.Valoracion;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseValoracion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioValoracion {

    private final ServicioBaseUsuario servicioBaseUsuario;
    private final ServicioBaseValoracion servicioBaseValoracion;
    private final ServicioBaseAnuncio servicioBaseAnuncio;

    public Double calcularMediaValoraciones(Usuario usuario) {

        Double media = servicioBaseValoracion.calcularMediaValoracionesUsuario(usuario.getId());

//        if(media == null)
//            throw  new  IllegalStateException("El usuario no tiene valoraciones");

        usuario.setReputacionMedia(media);
        servicioBaseUsuario.guardar(usuario);
        return media;

    }

    public Valoracion crearValoracion(Valoracion valoracion, Usuario evaluador) {
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(valoracion.getAnuncio().getId());

        if (servicioBaseValoracion.existePorAnuncioId(valoracion.getAnuncio().getId()))
            throw new IllegalStateException("Este anuncio ya ha sido valorado");

        if (!anuncio.getEstado().equals(Estado.CERRADO))
            throw new IllegalStateException("Solo se pueden valorar anuncios cerrados");

        Usuario evaluado = anuncio.getUsuario();

        if (evaluado.equals(evaluador))
            throw new IllegalStateException("No puedes valorarte a ti mismo");

        valoracion.setAnuncio(anuncio);
        valoracion.setEvaluado(evaluado);
        valoracion.setEvaluador(evaluador);
        Valoracion v =  servicioBaseValoracion.guardar(valoracion);
        this.calcularMediaValoraciones(evaluado);
        return v;
    }

    public Page<Valoracion> obtenerValoraciones(Pageable pageable, Usuario usuario) {

        return servicioBaseValoracion.buscarPorEvaluadoId(usuario.getId(),pageable);
    }
}
