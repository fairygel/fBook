package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.BookStatus;
import me.fairygel.fbook.repository.BookStatusReadOnlyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookStatusService {
    private final BookStatusReadOnlyRepository bookStatusRepository;

    public BookStatus read(Short id) {
        return bookStatusRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    public List<BookStatus> index() {
        List<BookStatus> bookStatuses = new ArrayList<>();

        bookStatusRepository.findAll().forEach(bookStatuses::add);
        return bookStatuses;
    }

}
