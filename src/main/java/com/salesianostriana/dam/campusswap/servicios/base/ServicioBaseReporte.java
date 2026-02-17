package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Reporte;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioReporte;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioBaseReporte {

    private final RepositorioReporte repositorioReporte;

    public Optional<Reporte> buscarPorAnuncioIdYUsuarioId(Long id, UUID idUsuario) {
        return repositorioReporte.findByAnuncioIdAndUsuarioId(id, idUsuario);
    }

    public Reporte guardar(Reporte reporte) {
        return repositorioReporte.save(reporte);
    }
}
