package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.type.BookTypeDTO;
import me.fairygel.fbook.dto.book.type.BookTypeIndexViewDTO;
import me.fairygel.fbook.entity.BookType;
import me.fairygel.fbook.mapper.BookTypeMapper;
import me.fairygel.fbook.repository.BookTypeReadOnlyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookTypeService {
    private final BookTypeReadOnlyRepository bookTypeRepository;
    private final BookTypeMapper mapper;

    public BookTypeDTO read(Short id) {
        BookType bookType = bookTypeRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return mapper.bookTypeToBookTypeDto(bookType);
    }

    public Set<BookTypeIndexViewDTO> index() {
        Set<BookType> bookTypes = new HashSet<>();

        bookTypeRepository.findAll().forEach(bookTypes::add);
        return mapper.bookTypesToIndex(bookTypes);
    }
}
