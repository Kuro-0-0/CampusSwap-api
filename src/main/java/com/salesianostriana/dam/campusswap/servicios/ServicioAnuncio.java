package com.salesianostriana.dam.campusswap.servicios;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.entidades.extras.Estado;
import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ServicioAnuncio {

    private final RepositorioAnuncio repositorioAnuncio;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCategoria repositorioCategoria;
    public Anuncio crearAnuncio(Anuncio anuncio) {
        Usuario usuario = repositorioUsuario.findById(anuncio.getUsuario().getId()).orElseThrow(() -> new NoSuchElementException("Usuario con ID " + anuncio.getUsuario().getId() + " no encontrado"));
        Categoria categoria = repositorioCategoria.findById(anuncio.getCategoria().getId()).orElseThrow(() -> new NoSuchElementException("Categoría con ID " + anuncio.getCategoria().getId() + " no encontrada"));

        anuncio.setEstado(Estado.ACTIVO);
        usuario.agregarAnuncio(anuncio);
        categoria.addAnuncio(anuncio);
        repositorioCategoria.save(categoria);
        repositorioUsuario.save(usuario);
        return repositorioAnuncio.save(anuncio);
    }


}
