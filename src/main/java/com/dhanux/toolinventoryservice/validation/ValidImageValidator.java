package com.dhanux.toolinventoryservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 24/03/2026 15:13
 * @ProjectDetails tool-inventory-service
 */
public class ValidImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // let @NotNull / @NotEmpty handle the null/empty case
        }
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
