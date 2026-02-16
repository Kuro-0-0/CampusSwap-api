
package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.repositorios.RepositorioUsuario;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.CheckExistenciaAnuncio;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class ValidadorExistenciaUsuario implements ConstraintValidator<CheckExistenciaAnuncio, String> {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Hola???: " + s);
        System.out.println(repositorioUsuario.existsById(UUID.fromString(s)));
        return repositorioUsuario.existsById(UUID.fromString(s));
    }
}
