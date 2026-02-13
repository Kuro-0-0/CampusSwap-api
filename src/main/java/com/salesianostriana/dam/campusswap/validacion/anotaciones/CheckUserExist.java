package com.salesianostriana.dam.campusswap.validacion.anotaciones;

import com.salesianostriana.dam.campusswap.validacion.validadores.CheckUserExistValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUserExistValidator.class)
@Documented
public @interface CheckUserExist {


    String message() default "{validation.check_user_exist.message}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

}
