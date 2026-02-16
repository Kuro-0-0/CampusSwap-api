package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorExistenciaAnuncio;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Constraint;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorExistenciaAnuncio.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckExistenciaAnuncio {

    String message() default "El anuncio con el ID proporcionado no existe";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};

}



