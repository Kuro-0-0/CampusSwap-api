package com.salesianostriana.dam.campusswap.validacion;

import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.AnuncioRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorPrecioSegunTipoOperacion implements ConstraintValidator<PrecioSegunTipoOperacion, AnuncioRequestDto> {


    @Override
    public boolean isValid(AnuncioRequestDto anuncioRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        if(anuncioRequestDto.tipoOperacion() == null) {
            return true;
        }

        boolean esVenta = anuncioRequestDto.tipoOperacion().equals(TipoOperacion.VENTA);
        boolean tienePrecio = anuncioRequestDto.precio() != null && anuncioRequestDto.precio() > 0;

        if(esVenta && !tienePrecio){
            return false;
        }

        if(!esVenta && anuncioRequestDto.precio() != null){
            return false;
        }

        return true;
    }
}
