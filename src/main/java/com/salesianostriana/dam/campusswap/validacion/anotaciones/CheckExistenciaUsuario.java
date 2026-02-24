package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorExistenciaUsuario;
import jakarta.validation.Constraint;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorExistenciaUsuario.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckExistenciaUsuario {

    String message() default "{validacion.usuario.existe}";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};

}



