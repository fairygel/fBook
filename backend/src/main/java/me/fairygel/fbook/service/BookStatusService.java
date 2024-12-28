package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.status.BookStatusDTO;
import me.fairygel.fbook.dto.book.status.BookStatusIndexViewDTO;
import me.fairygel.fbook.entity.BookStatus;
import me.fairygel.fbook.util.mapper.BookStatusMapper;
import me.fairygel.fbook.repository.BookStatusReadOnlyRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookStatusService {
    private final BookStatusReadOnlyRepository bookStatusRepository;
    private final BookStatusMapper mapper;

    public BookStatusDTO read(Short id) {
        BookStatus bookStatus = bookStatusRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return mapper.bookStatusToBookStatusDto(bookStatus);
    }

    public Set<BookStatusIndexViewDTO> index() {
        Set<BookStatus> bookStatuses = new HashSet<>();

        bookStatusRepository.findAll().forEach(bookStatuses::add);
        return mapper.bookStatusesToIndex(bookStatuses);
    }

}
