package me.fairygel.fbook.util;

import me.fairygel.fbook.entity.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class BookAutomation {
    public void automate(Book book) {
        if (book.getBookStatus() == null) return;

        if (Objects.equals(book.getBookStatus().getName(), "reading")) {
            book.setStartedReadDate(LocalDate.now());
        } else if (Objects.equals(book.getBookStatus().getName(), "finished")) {
            book.setEndedReadDate(LocalDate.now());
        }
    }
}
