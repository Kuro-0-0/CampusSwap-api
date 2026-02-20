package com.salesianostriana.dam.campusswap.servicios.base;

import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.repositorios.RepositorioMensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicioBaseMensaje {

    private final RepositorioMensaje repositorioMensaje;

    public Mensaje guardar(Mensaje mensaje) {
        return repositorioMensaje.save(mensaje);
    }

    public Page<Mensaje> buscarTodosPorAnuncioId(Long idAnuncio, Pageable pageable) {
        return repositorioMensaje.findAllByAnuncioId(idAnuncio, pageable);
    }

    public void borrar(Mensaje mensaje) {
        repositorioMensaje.delete(mensaje);
    }  
    
    public List<Mensaje> buscarTodosPorAnuncioIdYUsuarioId(Long idAnuncio, UUID idUsuario) {
        return repositorioMensaje.findAllByAnuncioIdAndUsuarioId(idAnuncio, idUsuario);
    }


    public List<Mensaje> buscarTodosPorUsuarioId(UUID id) {
        return repositorioMensaje.findAllByUsuarioId(id);
    }

    public List<Mensaje> buscarChatEspecifico(Long idAnuncio, String idContrario, String idActual) {
        return repositorioMensaje.findAllByAnuncioIdAndParticipantes(idAnuncio, UUID.fromString(idContrario), UUID.fromString(idActual));
    }
}
