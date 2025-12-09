package me.fairygel.fbook.util;

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
}
