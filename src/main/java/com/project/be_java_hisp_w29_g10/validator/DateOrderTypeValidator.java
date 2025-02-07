package com.project.be_java_hisp_w29_g10.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.project.be_java_hisp_w29_g10.enums.DateOrderType;

public class DateOrderTypeValidator implements ConstraintValidator<ValidDateOrderType, String> {

    @Override
    public void initialize(ValidDateOrderType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are allowed
        }
        try {
            DateOrderType.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}