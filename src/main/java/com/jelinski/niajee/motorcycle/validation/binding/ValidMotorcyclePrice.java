package com.jelinski.niajee.motorcycle.validation.binding;

import com.jelinski.niajee.motorcycle.validation.validator.MotorcyclePriceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MotorcyclePriceValidator.class)
@Documented
public @interface ValidMotorcyclePrice {

    String message() default "Motorcycle price should be lower than {limit}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int limit() default 1000000;
}
