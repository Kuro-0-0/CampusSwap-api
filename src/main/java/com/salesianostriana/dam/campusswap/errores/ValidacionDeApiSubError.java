package com.salesianostriana.dam.campusswap.errores;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Builder
public record ValidacionDeApiSubError(
        String objeto,
        String mesnseaje,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String campo,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String valorRechazado
) {

    public ValidacionDeApiSubError(String objeto, String mensaje){
        this(objeto, mensaje, null, null);
    }

    public static ValidacionDeApiSubError from(ObjectError error){
        ValidacionDeApiSubError resultado = null;

        if(error instanceof FieldError fieldError){
            resultado = ValidacionDeApiSubError.builder()
                    .objeto(error.getObjectName())
                    .mesnseaje(error.getDefaultMessage())
                    .campo(fieldError.getField())
                    .valorRechazado(String.valueOf(fieldError.getRejectedValue()))
                    .build();
        }else{
            resultado = ValidacionDeApiSubError.builder()
                    .objeto(error.getObjectName())
                    .mesnseaje(error.getDefaultMessage())
                    .build();
        }

        return resultado;
    }
}
