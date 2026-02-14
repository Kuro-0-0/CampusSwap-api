package com.salesianostriana.dam.campusswap.repositorios;

import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje,Long> {
}
