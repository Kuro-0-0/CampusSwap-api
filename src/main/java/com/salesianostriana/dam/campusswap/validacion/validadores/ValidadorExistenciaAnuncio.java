package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.repositorios.RepositorioAnuncio;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaAnuncio;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidadorExistenciaAnuncio implements ConstraintValidator<CheckExistenciaAnuncio, Long> {

    @Autowired
    private RepositorioAnuncio repositorioAnuncio;

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return repositorioAnuncio.existsById(aLong);
    }
}
