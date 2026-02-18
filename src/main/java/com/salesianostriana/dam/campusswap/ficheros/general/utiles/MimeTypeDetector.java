package com.salesianostriana.dam.campusswap.ficheros.general.utiles;

import org.springframework.core.io.Resource;


public interface MimeTypeDetector {

    String getMimeType(Resource resource);

}
