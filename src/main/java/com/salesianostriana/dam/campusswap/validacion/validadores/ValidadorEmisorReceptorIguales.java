package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.entidades.extras.dtos.mensaje.EnviarMensajeRequestDto;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.EmisorReceptorIguales;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorEmisorReceptorIguales implements ConstraintValidator<EmisorReceptorIguales, EnviarMensajeRequestDto> {
    @Override
    public boolean isValid(EnviarMensajeRequestDto enviarMensajeRequestDto, ConstraintValidatorContext constraintValidatorContext) {

        // Tras securizar, ha quedado inutil.

//        if (enviarMensajeRequestDto.receptorId() == null || enviarMensajeRequestDto.emisorId() == null) {
//            return true;
//        }
//
//        if(enviarMensajeRequestDto.receptorId().equals(enviarMensajeRequestDto.emisorId())) {
//            return false;
//        }

        return true;
    }
}
