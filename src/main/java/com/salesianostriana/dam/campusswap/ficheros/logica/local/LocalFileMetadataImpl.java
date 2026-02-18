package com.salesianostriana.dam.campusswap.ficheros.logica.local;

import com.salesianostriana.dam.campusswap.ficheros.general.model.AbstractFileMetadata;
import com.salesianostriana.dam.campusswap.ficheros.general.model.FileMetadata;
import lombok.experimental.SuperBuilder;


@SuperBuilder
public class LocalFileMetadataImpl extends AbstractFileMetadata {

    public static FileMetadata of(String filename) {
        return LocalFileMetadataImpl.builder()
                .id(filename)
                .filename(filename)
                .build();
    }

}
