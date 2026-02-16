package com.salesianostriana.dam.campusswap.entidades.extras;

public enum Motivo {
    FRAUDE("Fraude"),
    CONTENIDO_INAPROPIADO("Contenido inapropiado"),
    SPAM("Spam"),
    OTRO("Otro");

    private final String descripcion;

    Motivo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
