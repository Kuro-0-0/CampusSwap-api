package com.salesianostriana.dam.campusswap.errores.custom;

public class NonExistentImageException extends RuntimeException {
    public NonExistentImageException(String message) {
        super(message);
    }
}
