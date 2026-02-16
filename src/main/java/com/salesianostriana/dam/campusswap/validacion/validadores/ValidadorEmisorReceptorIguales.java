package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.EnviarMensajeRequestDto;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.EmisorReceptorIguales;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorEmisorReceptorIguales implements ConstraintValidator<EmisorReceptorIguales, EnviarMensajeRequestDto> {
    @Override
    public boolean isValid(EnviarMensajeRequestDto enviarMensajeRequestDto, ConstraintValidatorContext constraintValidatorContext) {

        if(enviarMensajeRequestDto.receptorId().equals(enviarMensajeRequestDto.emisorId())) {
            return false;
        }

        return true;
    }
}
