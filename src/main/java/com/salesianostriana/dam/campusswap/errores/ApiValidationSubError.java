package com.salesianostriana.dam.campusswap.errores;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Builder
public record ApiValidationSubError(

        String objecto,
        String mensaje,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String campo,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String valorRechazado
) {

    public ApiValidationSubError(String object, String message) {
        this(object, message, null, null);
    }

    public static ApiValidationSubError from(ObjectError error) {
        ApiValidationSubError result = null;

        if (error instanceof FieldError fieldError) {
            result = ApiValidationSubError.builder()
                    .objecto(error.getObjectName())
                    .mensaje(error.getDefaultMessage())
                    .campo(fieldError.getField())
                    .valorRechazado(fieldError.getRejectedValue().toString())
                    .build();
        } else {
            result = ApiValidationSubError.builder()
                    .objecto(error.getObjectName())
                    .mensaje(error.getDefaultMessage())
                    .build();
        }

        return result;
    }

}