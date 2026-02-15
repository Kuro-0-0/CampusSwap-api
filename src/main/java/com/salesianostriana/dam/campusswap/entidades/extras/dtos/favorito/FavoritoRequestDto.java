package com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FavoritoRequestDto(
        @NotBlank(message = "El ID de usuario no puede estar vacío")
        String usuarioId,
        @NotNull(message = "El ID del anuncio no puede ser nulo")
        Long anuncioId
) {
}
