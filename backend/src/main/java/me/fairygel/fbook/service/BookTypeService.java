package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dao.BookTypeReadOnlyDAO;
import me.fairygel.fbook.entity.BookType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookTypeService {
    private final BookTypeReadOnlyDAO bookTypeDAO;

    public BookType read(long id) {
        return bookTypeDAO.read(id);
    }

    public List<BookType> index() {
        return bookTypeDAO.index();
    }
}
