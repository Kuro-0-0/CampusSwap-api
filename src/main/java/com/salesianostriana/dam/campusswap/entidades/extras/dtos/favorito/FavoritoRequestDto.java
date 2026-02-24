package com.salesianostriana.dam.campusswap.entidades.extras.dtos.favorito;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FavoritoRequestDto(
        @NotNull(message = "{favorito.anuncioId.notnull}")
        Long anuncioId
) {
}
