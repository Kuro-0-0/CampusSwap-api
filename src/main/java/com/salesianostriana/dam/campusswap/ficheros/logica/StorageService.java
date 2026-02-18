package com.salesianostriana.dam.campusswap.ficheros.logica;

import com.salesianostriana.dam.campusswap.ficheros.general.model.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    FileMetadata store(MultipartFile file);

    Resource loadAsResource(String id);

    void deleteFile(String filename);


}
