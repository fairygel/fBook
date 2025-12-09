package me.fairygel.fbook.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BookDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBookDates {
    String message() default "Invalid book dates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

