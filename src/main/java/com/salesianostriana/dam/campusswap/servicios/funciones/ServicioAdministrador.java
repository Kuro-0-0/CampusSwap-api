package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioAdministrador {

    private final ServicioBaseUsuario servicioBaseUsuario;

    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return servicioBaseUsuario.listarUsuarios(pageable);
    }

}
