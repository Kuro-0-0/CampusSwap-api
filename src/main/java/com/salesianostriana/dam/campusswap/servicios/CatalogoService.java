package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.AnuncioResponseDto;
import com.salesianostriana.dam.campusswap.especificaciones.AnuncioEspecificacion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final RepositorioAnuncio repositorioAnuncio;

    public Page<Anuncio> obtenerCatalogo(Pageable pageable, String q) {

        return repositorioAnuncio.findBy(AnuncioEspecificacion.buscarPorQuery(q).and(AnuncioEspecificacion.soloActivos()), p -> p.page(pageable));
    }
}
