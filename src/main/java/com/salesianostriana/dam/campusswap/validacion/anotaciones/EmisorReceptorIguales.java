package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorEmisorReceptorIguales;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidadorEmisorReceptorIguales.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmisorReceptorIguales {
    String message() default "El emisor no puede ser el mismo que el receptor";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
