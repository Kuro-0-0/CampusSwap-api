package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.ValidadorPrecioSegunTipoOperacion;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidadorPrecioSegunTipoOperacion.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PrecioSegunTipoOperacion {
    String message() default "{validacion.precio.tipooperacion}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
