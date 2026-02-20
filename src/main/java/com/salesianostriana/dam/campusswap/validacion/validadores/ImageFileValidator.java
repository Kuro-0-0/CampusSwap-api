package com.salesianostriana.dam.campusswap.validacion.validadores;

import com.salesianostriana.dam.campusswap.validacion.anotaciones.ValidImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        String contentType = file.getContentType();

        System.out.println(contentType);

        return contentType != null && contentType.startsWith("image/");
    }
}