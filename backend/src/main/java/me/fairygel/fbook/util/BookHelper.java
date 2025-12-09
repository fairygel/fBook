package me.fairygel.fbook.util;

import jakarta.validation.ValidationException;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.entity.BookStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class BookHelper {
    public void automate(Book book, BookStatus previousStatus) {
        if (book.getBookStatus() == null) return;
        String statusName = book.getBookStatus().getName();

        if ( Objects.equals(statusName, previousStatus.getName()) ) return;

        if ( Objects.equals(statusName, "reading") && book.getStartedReadDate() == null ) {
            book.setStartedReadDate(LocalDate.now());
        } else if (Objects.equals(statusName, "finished") && book.getEndedReadDate() == null ) {
            book.setEndedReadDate(LocalDate.now());
            if ( book.getStartedReadDate() == null ) {
                book.setStartedReadDate(LocalDate.now());
            }
        }
    }

    public void validate (Book book) {
        boolean hasStarted = book.getStartedReadDate() != null;
        boolean hasEnded = book.getEndedReadDate() != null;

        LocalDate startedDate = book.getStartedReadDate();
        LocalDate endedDate = book.getEndedReadDate();

        if ( hasEnded && !hasStarted ) {
            throw new ValidationException("Started read date cannot be null if ended read date is provided.");
        }

        if ( hasStarted ) {
            if (isFutureDate(startedDate)) {
                throw new ValidationException("Started read date cannot be in the future.");
            }

            if ( hasEnded ) {
                if (endedDate.isBefore(startedDate)) {
                    throw new ValidationException("Ended read date cannot be before started read date.");
                }

                if (isFutureDate(endedDate)) {
                    throw new ValidationException("Ended read date cannot be in the future.");
                }
            }
        }
    }

    private boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}
