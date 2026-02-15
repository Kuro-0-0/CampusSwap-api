package com.salesianostriana.dam.campusswap.errores.custom;

public class NotOwnedException extends RuntimeException {
    public NotOwnedException(String message) {
        super(message);
    }
}
