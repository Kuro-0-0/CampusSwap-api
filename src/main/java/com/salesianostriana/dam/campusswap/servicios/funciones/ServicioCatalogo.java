package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioFiltroDto;
import com.salesianostriana.dam.campusswap.especificaciones.AnuncioEspecificacion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioCatalogo {

    private final ServicioBaseAnuncio servicioBaseAnuncio;

    public Page<Anuncio> obtenerCatalogo(Pageable pageable, AnuncioFiltroDto filtro, Usuario usuario) {

        PredicateSpecification<Anuncio> pred = PredicateSpecification.allOf(
                AnuncioEspecificacion.buscarPorQuery(filtro.q()),
                AnuncioEspecificacion.porCategoria(filtro.categoriaId()),
                AnuncioEspecificacion.porPrecioRango(filtro.minPrecio(), filtro.maxPrecio()),
                AnuncioEspecificacion.porTipoOperacion(filtro.tipoOperacion()),
                AnuncioEspecificacion.porEstado(filtro.estado()),
                AnuncioEspecificacion.excluirPropios(usuario)
        );

        return servicioBaseAnuncio.buscarFiltradoPageado(pred, pageable);
    }

    public Page<Anuncio> obtenerTodosLosAnunciosAdmin(Pageable pageable, AnuncioFiltroDto filtro){

        PredicateSpecification<Anuncio> pred = PredicateSpecification.allOf(
                AnuncioEspecificacion.buscarPorQuery(filtro.q()),
                AnuncioEspecificacion.porCategoria(filtro.categoriaId()),
                AnuncioEspecificacion.porPrecioRango(filtro.minPrecio(), filtro.maxPrecio()),
                AnuncioEspecificacion.porTipoOperacion(filtro.tipoOperacion()),
                AnuncioEspecificacion.porEstado(filtro.estado())
        );

        return servicioBaseAnuncio.buscarFiltradoPageado(pred, pageable);

    }

}
