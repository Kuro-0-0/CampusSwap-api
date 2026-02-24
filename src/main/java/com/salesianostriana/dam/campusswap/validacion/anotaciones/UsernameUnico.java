package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorUsernameUnico;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorUsernameUnico.class)
@Documented
public @interface UsernameUnico {
    String message() default "{validacion.username.unico}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
