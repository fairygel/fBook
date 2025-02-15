package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.fairygel.fbook.dto.book.BookCoverDTO;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.entity.BookCover;
import me.fairygel.fbook.repository.BookCoverCrudRepository;
import me.fairygel.fbook.util.mapper.BookCoverMapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class BookCoverService {
    private final BookCoverCrudRepository coverRepository;
    private final BookCoverMapperImpl mapper;

    @SneakyThrows
    public void createCover(Book book, MultipartFile cover) {
        if (cover == null) return;

        BookCover bookCover = mapper.coverFromMultipartFile(book, cover);

        coverRepository.save(bookCover);
    }

    public BookCoverDTO getCover(Long bookId) {
        BookCover cover = coverRepository.findByBookId(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No cover for book with id = " + bookId));

        return mapper.bookCoverToDTO(cover);
    }

    @Transactional
    public void deleteCover(Long bookId) {
        coverRepository.deleteByBookId(bookId);
    }

    @SneakyThrows
    public void updateCover(Book updatedBook, MultipartFile cover) {
        if (cover == null) return;

        if (updatedBook.getCover() == null) createCover(updatedBook, cover);
        else {
            BookCover bookCover = mapper.coverFromMultipartFile(updatedBook, cover);
            coverRepository.updateById(updatedBook.getId(), bookCover);
        }
    }
}
