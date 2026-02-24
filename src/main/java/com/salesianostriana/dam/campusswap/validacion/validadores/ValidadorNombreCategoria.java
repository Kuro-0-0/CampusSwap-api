package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.repositorios.RepositorioCategoria;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.UniqueNombreCategoria;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidadorNombreCategoria implements ConstraintValidator<UniqueNombreCategoria, String> {

    private final RepositorioCategoria repositorioCategoria;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return !repositorioCategoria.existsByNombreIgnoreCase(value);
    }
}
