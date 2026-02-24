package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorContrasenaNoCoincide;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidadorContrasenaNoCoincide.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContrasenaNoCoincide {
    String message() default "{validacion.contrasena.nocoincide}";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
