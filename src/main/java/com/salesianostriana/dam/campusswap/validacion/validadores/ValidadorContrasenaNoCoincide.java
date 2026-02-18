package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.seguridad.auth.dto.RegisterRequest;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.ContrasenaNoCoincide;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorContrasenaNoCoincide implements ConstraintValidator<ContrasenaNoCoincide, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (registerRequest.password() == null || registerRequest.repeatPassword() == null) {
            return true; // Deja que otras validaciones manejen la nulidad
        }
        return registerRequest.password().equals(registerRequest.repeatPassword());
    }
}
