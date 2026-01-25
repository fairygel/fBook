package me.fairygel.fbook.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.fairygel.fbook.entity.Book;

import java.time.LocalDate;

public class BookDatesValidator implements ConstraintValidator<ValidBookDates, Book> {

    @Override
    public void initialize(ValidBookDates constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Book book, ConstraintValidatorContext context) {
        if (book == null) return true;

        LocalDate startedDate = book.getStartedReadDate();
        LocalDate endedDate = book.getEndedReadDate();

        boolean hasStarted = startedDate != null;
        boolean hasEnded = endedDate != null;

        context.disableDefaultConstraintViolation();

        if (hasEnded && !hasStarted) {
            context.buildConstraintViolationWithTemplate("Started read date cannot be null if ended read date is provided.")
                    .addPropertyNode("startedReadDate")
                    .addConstraintViolation();
            return false;
        }

        if (hasStarted) {
            if (isFutureDate(startedDate)) {
                context.buildConstraintViolationWithTemplate("Started read date cannot be in the future.")
                        .addPropertyNode("startedReadDate")
                        .addConstraintViolation();
                return false;
            }

            if (hasEnded) {
                if (endedDate.isBefore(startedDate)) {
                    context.buildConstraintViolationWithTemplate("Ended read date cannot be before started read date.")
                            .addPropertyNode("endedReadDate")
                            .addConstraintViolation();
                    return false;
                }

                if (isFutureDate(endedDate)) {
                    context.buildConstraintViolationWithTemplate("Ended read date cannot be in the future.")
                            .addPropertyNode("endedReadDate")
                            .addConstraintViolation();
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}

