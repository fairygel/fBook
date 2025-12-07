package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.BookStatusDTO;
import me.fairygel.fbook.entity.BookStatus;
import me.fairygel.fbook.repository.BookStatusReadOnlyRepository;
import me.fairygel.fbook.util.mapper.BookStatusMapperImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookStatusService {
    private final BookStatusReadOnlyRepository bookStatusRepository;
    private final BookStatusMapperImpl mapper;

    public BookStatusDTO read(Short id) {
        BookStatus bookStatus = bookStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No book status with ID = " + id));
        return mapper.bookStatusToBookStatusDto(bookStatus);
    }

    public Set<BookStatusDTO> index() {
        Set<BookStatus> bookStatuses = new HashSet<>();

        bookStatusRepository.findAll().forEach(bookStatuses::add);
        return mapper.bookStatusesToIndex(bookStatuses);
    }

}
