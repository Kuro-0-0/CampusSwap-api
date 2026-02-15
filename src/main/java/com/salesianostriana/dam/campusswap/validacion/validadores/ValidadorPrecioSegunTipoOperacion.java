package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.entidades.extras.TipoOperacion;
import com.salesianostriana.dam.campusswap.entidades.extras.dtos.anuncio.crear.CrearAnuncioRequestDto;
import com.salesianostriana.dam.campusswap.validacion.anotaciones.PrecioSegunTipoOperacion;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorPrecioSegunTipoOperacion implements ConstraintValidator<PrecioSegunTipoOperacion, CrearAnuncioRequestDto> {


    @Override
    public boolean isValid(CrearAnuncioRequestDto anuncioRequestDto, ConstraintValidatorContext constraintValidatorContext) {
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
