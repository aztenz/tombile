package com.n2o.tombile.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {
    private Set<String> allowedValuesSet;
    private String validValues;
    private boolean ignoreCase;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
        String[] allowedValues = constraintAnnotation.allowedValues();

        if (allowedValues.length > 0) {
            if (ignoreCase) {
                this.allowedValuesSet = new HashSet<>(Arrays.asList(allowedValues)).stream()
                        .map(String::toUpperCase)
                        .collect(Collectors.toSet());
            } else {
                this.allowedValuesSet = new HashSet<>(Arrays.asList(allowedValues));
            }
        } else {
            if (ignoreCase) {
                this.allowedValuesSet = Arrays.stream(enumClass.getEnumConstants())
                        .map(enumValue -> enumValue.name().toUpperCase())
                        .collect(Collectors.toSet());
            } else {
                this.allowedValuesSet = Arrays.stream(enumClass.getEnumConstants())
                        .map(Enum::name)
                        .collect(Collectors.toSet());
            }
        }

        this.validValues = String.join(", ", this.allowedValuesSet);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Consider null values valid. Use @NotNull for null checks if needed.
        }

        if (ignoreCase) {
            value = value.toUpperCase();
        }

        if (!allowedValuesSet.contains(value)) {
            String message = "Role must be one of: " + validValues;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
