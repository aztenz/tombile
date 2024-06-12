package com.n2o.tombile.core.common.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEmptyIfPresentValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyIfPresent {
    String message() default "must not be empty if present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

