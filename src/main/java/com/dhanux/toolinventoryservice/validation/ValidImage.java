package com.dhanux.toolinventoryservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Dhanujaya(Dhanu)
 * @TimeStamp 24/03/2026 15:14
 * @ProjectDetails tool-inventory-service
 */
@Documented
@Constraint(validatedBy = ValidImageValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImage {

    String message() default "Only image files are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
