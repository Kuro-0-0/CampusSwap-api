package com.salesianostriana.dam.campusswap.ficheros.general.excepciones;

public class StorageException extends RuntimeException{

    public StorageException(String msg) {
        super(msg);
    }

    public StorageException(String msg, Exception e) {
        super(msg, e);
    }

}
