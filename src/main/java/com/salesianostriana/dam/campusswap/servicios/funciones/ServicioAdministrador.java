package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import com.salesianostriana.dam.campusswap.entidades.Categoria;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseCategoria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioAdministrador {

    private final ServicioBaseUsuario servicioBaseUsuario;
    private final ServicioBaseAnuncio servicioBaseAnuncio;
    private final ServicioBaseCategoria servicioBaseCategoria;

    public Categoria crearCategoria(Categoria categoria) {
        return servicioBaseCategoria.guardar(categoria);
    }

    public Categoria obtenerCategoria(Long id) {
        return servicioBaseCategoria.buscarPorId(id);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        Categoria categoriaExistente = servicioBaseCategoria.buscarPorId(id);

        if (!categoriaExistente.getNombre().equals(categoria.getNombre()))
            if (servicioBaseCategoria.existePorNombre(categoria.getNombre()))
                throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoria.getNombre());

        return servicioBaseCategoria.guardar(categoriaExistente.modificar(categoria));
    }

    public void eliminarCategoria(Long id) {
        if (servicioBaseAnuncio.existByCategoriaId(id))
            throw new IllegalStateException("No se puede eliminar la categoría porque hay anuncios asociados a ella.");

        servicioBaseCategoria.borrar(servicioBaseCategoria.buscarPorId(id));
    }

    public Page<Categoria> listarCategorias(String nombre, Pageable pageable) {
        PredicateSpecification<Categoria> spec = (from, builder) ->
                (nombre != null && !nombre.isEmpty())
                        ? builder.like(builder.lower(from.get("nombre")), "%" + nombre.toLowerCase() + "%")
                        : builder.and();

        return servicioBaseCategoria.buscarTodos(spec, pageable);
    }

    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return servicioBaseUsuario.listarUsuarios(pageable);
    }

    public Usuario bloquearUsuario(UUID id){
        Usuario usuario = servicioBaseUsuario.buscarPorId(id);
        usuario.setAccountNonLocked(false);
        return servicioBaseUsuario.guardar(usuario);
    }

}
