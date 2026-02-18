package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioBaseUsuario
{

    private final RepositorioUsuario repositorioUsuario;

    public Usuario buscarPorId(String uuid) {
        return buscarPorId(UUID.fromString(uuid));
    }

    public Usuario buscarPorId(UUID uuid) {
        return repositorioUsuario.findById(uuid).orElseThrow(
                () -> new NoSuchElementException("Usuario con ID " + uuid + " no encontrado")
        );
    }


    public Usuario guardar(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }
}
