package com.project.be_java_hisp_w29_g10.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.project.be_java_hisp_w29_g10.enums.NameOrderType;

public class NameOrderTypeValidator implements ConstraintValidator<ValidNameOrderType, String> {

    @Override
    public void initialize(ValidNameOrderType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are allowed
        }
        try {
            NameOrderType.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
