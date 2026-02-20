package com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.chat;

import com.salesianostriana.dam.campusswap.entidades.Usuario;

public record UsuarioChatDto(
        String id,
        String nombre,
        boolean yo
) {

    public static UsuarioChatDto of(Usuario usuario, boolean esYo) {
        return new UsuarioChatDto(usuario.getId().toString(), usuario.getNombre(), esYo);
    }

}
