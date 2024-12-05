package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dao.BookStatusReadOnlyDAO;
import me.fairygel.fbook.entity.BookStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookStatusService {
    private final BookStatusReadOnlyDAO bookDAO;

    public BookStatus read(Long id) {
        return bookDAO.read(id);
    }

    public List<BookStatus> index() {
        return bookDAO.index();
    }

}
