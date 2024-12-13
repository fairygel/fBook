package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.BookType;
import me.fairygel.fbook.repository.BookTypeReadOnlyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookTypeService {
    private final BookTypeReadOnlyRepository bookTypeRepository;

    public BookType read(Short id) {
        return bookTypeRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    public List<BookType> index() {
        List<BookType> bookTypes = new ArrayList<>();

        bookTypeRepository.findAll().forEach(bookTypes::add);
        return bookTypes;
    }
}
