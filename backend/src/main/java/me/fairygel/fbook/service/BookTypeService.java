package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.BookTypeDTO;
import me.fairygel.fbook.entity.BookType;
import me.fairygel.fbook.repository.BookTypeReadOnlyRepository;
import me.fairygel.fbook.util.mapper.BookTypeMapperImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookTypeService {
    private final BookTypeReadOnlyRepository bookTypeRepository;
    private final BookTypeMapperImpl mapper;

    public BookTypeDTO read(Short id) {
        BookType bookType = bookTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No book type with ID = " + id));
        return mapper.bookTypeToBookTypeDto(bookType);
    }

    public Set<BookTypeDTO> index() {
        Set<BookType> bookTypes = new HashSet<>();

        bookTypeRepository.findAll().forEach(bt -> {if (bt.getId() != 0L) bookTypes.add(bt);});
        return mapper.bookTypesToIndex(bookTypes);
    }
}
