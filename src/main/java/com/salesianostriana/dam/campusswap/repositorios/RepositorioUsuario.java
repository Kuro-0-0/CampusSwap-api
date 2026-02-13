package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {
}
