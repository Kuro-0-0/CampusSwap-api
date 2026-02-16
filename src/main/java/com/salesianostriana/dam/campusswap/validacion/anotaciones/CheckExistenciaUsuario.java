package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorExistenciaAnuncio;
import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorExistenciaUsuario;
import jakarta.validation.Constraint;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorExistenciaUsuario.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckExistenciaUsuario {

    String message() default "El usuario con el ID proporcionado no existe";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};

}



