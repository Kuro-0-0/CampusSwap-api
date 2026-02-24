package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorNombreCategoria;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorNombreCategoria.class)
@Documented
public @interface UniqueNombreCategoria {

    String message() default "{validacion.categoria.nombreunico}";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};


}
