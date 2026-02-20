package com.salesianostriana.dam.campusswap.servicios.funciones;

import com.salesianostriana.dam.campusswap.controladores.ControladorMensaje;
import com.salesianostriana.dam.campusswap.entidades.Anuncio;
import com.salesianostriana.dam.campusswap.entidades.Mensaje;
import com.salesianostriana.dam.campusswap.entidades.Usuario;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.ListarChatResponseDto;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseAnuncio;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseMensaje;
import com.salesianostriana.dam.campusswap.servicios.base.ServicioBaseUsuario;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Log
@Service
@RequiredArgsConstructor
public class ServicioMensaje {

    private final ServicioBaseMensaje servicioBaseMensaje;
    private final ServicioBaseUsuario servicioBaseUsuario;
    private final ServicioBaseAnuncio servicioBaseAnuncio;

    public Mensaje enviarMensaje(Mensaje mensaje, Usuario usuario) {
        Usuario receptor = servicioBaseUsuario.buscarPorId(mensaje.getReceptor().getId());
        Anuncio anuncio = servicioBaseAnuncio.buscarPorId(mensaje.getAnuncio().getId());

        mensaje.setEmisor(usuario);
        mensaje.setReceptor(receptor);
        mensaje.setAnuncio(anuncio);

        return servicioBaseMensaje.guardar(mensaje);
    }

    public Page<Mensaje> obtenerMensajes(Long idAnuncio, Pageable pageable) {
        if (!servicioBaseAnuncio.existePorId(idAnuncio))
            throw new NoSuchElementException("No se ha encontrado el anuncio con id: " + idAnuncio);
        return servicioBaseMensaje.buscarTodosPorAnuncioId(idAnuncio, pageable);
    }


    public List<ListarChatResponseDto> obtenerChats(Usuario usuario) {
        List<ListarChatResponseDto> chats = new ArrayList<>();
        List<Mensaje> mensajes = servicioBaseMensaje.buscarTodosPorUsuarioId(usuario.getId());
        Map<Long, List<Mensaje>> mensajesPorAnuncio = new HashMap<>();

        for (Mensaje m : mensajes) {
            Set<String> participantes = new HashSet<>();
            participantes.add(m.getEmisor().getId().toString());
            participantes.add(m.getReceptor().getId().toString());
            ListarChatResponseDto chat = new ListarChatResponseDto(m.getAnuncio().getId(), participantes);

            if (!chatExiste(chats, chat)) {
                chats.add(chat);
            }
        }

        return chats;
    }

    public boolean chatExiste(List<ListarChatResponseDto> listaChats, ListarChatResponseDto chatActual) {
        for (ListarChatResponseDto chat : listaChats) {
            if (chat.idAnuncio().equals(chatActual.idAnuncio()) &&
                    chat.participantes().equals(chatActual.participantes())) {
                return true;
            }
        }
        return false;
    }

    public Optional<ListarChatResponseDto> buscarChatEspecifico(List<ListarChatResponseDto> listaChats, Long idAnuncio, String idContrario, String idUsuario) {
        for (ListarChatResponseDto chat : listaChats) {
            if (chat.idAnuncio().equals(idAnuncio) &&
                    chat.participantes().contains(idContrario) && chat.participantes().contains(idUsuario)) {
                return Optional.of(chat);
            }
        }
        return Optional.empty();
    }

    public Page<Mensaje> obtenerChatEspecifico(Long idAnuncio, String idContrario, Usuario usuario, Pageable pageable) {
        List<ListarChatResponseDto> chats = obtenerChats(usuario);
        ListarChatResponseDto chatEspecifico = buscarChatEspecifico(chats, idAnuncio, idContrario, usuario.getId().toString())
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el chat para el anuncio con id: " + idAnuncio + " y los participantes con id: " + idContrario + " y " + usuario.getId()));

        return servicioBaseMensaje.buscarChatEspecifico(idAnuncio, idContrario, usuario.getId().toString(), pageable);

    }
}
