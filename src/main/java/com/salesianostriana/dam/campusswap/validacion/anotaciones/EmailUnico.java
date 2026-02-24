package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorEmailUnico;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorEmailUnico.class)
@Documented
public @interface EmailUnico {
    String message() default "{validacion.email.unico}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
