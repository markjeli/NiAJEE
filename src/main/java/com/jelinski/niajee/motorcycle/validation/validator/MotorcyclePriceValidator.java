package com.jelinski.niajee.motorcycle.validation.validator;

import com.jelinski.niajee.motorcycle.validation.binding.ValidMotorcyclePrice;
import jakarta.validation.ConstraintValidator;

public class MotorcyclePriceValidator implements ConstraintValidator<ValidMotorcyclePrice, Integer> {

    private int limit;

    @Override
    public void initialize(ValidMotorcyclePrice constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        limit = constraintAnnotation.limit();
    }

    @Override
    public boolean isValid(Integer value, jakarta.validation.ConstraintValidatorContext context) {
        return value < limit;
    }
}
