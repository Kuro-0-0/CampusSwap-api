package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
}
