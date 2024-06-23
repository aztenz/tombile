package com.n2o.tombile.core.common.validate.impl;

import com.n2o.tombile.core.common.validate.annotation.ImageFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE
    );

    private long maxSize;

    @Override
    public void initialize(ImageFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        boolean validType = ALLOWED_CONTENT_TYPES.contains(file.getContentType());
        boolean validSize = file.getSize() <= maxSize;
        return validType && validSize;
    }
}