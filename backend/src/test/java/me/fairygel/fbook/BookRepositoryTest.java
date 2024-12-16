package me.fairygel.fbook;

import me.fairygel.fbook.entity.*;
import me.fairygel.fbook.repository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Testcontainers
class BookRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.15-alpine");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    BookCrudRepository bookRepository;

    @Autowired
    AuthorCrudRepository authorRepository;
    @Autowired
    BookStatusReadOnlyRepository bookStatusRepository;
    @Autowired
    BookTypeReadOnlyRepository bookTypeRepository;
    @Autowired
    GenreCrudRepository genreRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    Book book;

    @BeforeEach
    void createBook() {
        book = new Book();

        book.setName("book");

        Optional<Author> author = authorRepository.findById(0L);
        Optional<BookStatus> bookStatus = bookStatusRepository.findById((short) 0);
        Optional<BookType> bookType = bookTypeRepository.findById((short) 0);
        Optional<Genre> genre = genreRepository.findById(0L);

        assertTrue(author.isPresent());
        assertTrue(bookStatus.isPresent());
        assertTrue(bookType.isPresent());
        assertTrue(genre.isPresent());

        book.setAuthor(author.get());
        book.setBookStatus(bookStatus.get());
        book.setBookType(bookType.get());
        book.setAnnotation("annotation");
        book.setStartedReadDate(LocalDate.of(2020, 1, 1));
        book.setEndedReadDate(LocalDate.of(2023, 2, 3));
        book.setGenres(Set.of(genre.get()));

        bookRepository.save(book);
    }

    @Test
    void errorWhenNameIsNull() {
        Book problemBook = new Book();
        problemBook.setName(null);

        Optional<Author> author = authorRepository.findById(0L);
        Optional<BookStatus> bookStatus = bookStatusRepository.findById((short) 0);
        Optional<BookType> bookType = bookTypeRepository.findById((short) 0);

        assertTrue(author.isPresent());
        assertTrue(bookStatus.isPresent());
        assertTrue(bookType.isPresent());

        problemBook.setAuthor(author.get());
        problemBook.setBookStatus(bookStatus.get());
        problemBook.setBookType(bookType.get());

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(problemBook));
    }

    @Test
    void errorWhenAuthorIsNull() {
        Book problemBook = new Book();
        problemBook.setName("not null");

        Optional<BookStatus> bookStatus = bookStatusRepository.findById((short) 0);
        Optional<BookType> bookType = bookTypeRepository.findById((short) 0);

        assertTrue(bookStatus.isPresent());
        assertTrue(bookType.isPresent());

        problemBook.setAuthor(null);
        problemBook.setBookStatus(bookStatus.get());
        problemBook.setBookType(bookType.get());

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(problemBook));
    }

    @Test
    void errorWhenBookStatusIsNull() {
        Book problemBook = new Book();
        problemBook.setName("not null");

        Optional<Author> author = authorRepository.findById(0L);
        Optional<BookType> bookType = bookTypeRepository.findById((short) 0);

        assertTrue(author.isPresent());
        assertTrue(bookType.isPresent());

        problemBook.setAuthor(author.get());
        problemBook.setBookStatus(null);
        problemBook.setBookType(bookType.get());

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(problemBook));
    }

    @Test
    void errorWhenBookTypeIsNull() {
        Book problemBook = new Book();
        problemBook.setName("not null");

        Optional<BookStatus> bookStatus = bookStatusRepository.findById((short) 0);
        Optional<Author> author = authorRepository.findById(0L);

        assertTrue(bookStatus.isPresent());
        assertTrue(author.isPresent());

        problemBook.setAuthor(author.get());
        problemBook.setBookStatus(bookStatus.get());
        problemBook.setBookType(null);

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(problemBook));
    }

    @Test
    void findByIdBookReallyFindsABook() {
        Optional<Book> optionalFoundBook = bookRepository.findById(book.getId());

        assertTrue(optionalFoundBook.isPresent());

        Book foundBook = optionalFoundBook.get();

        assertEquals(book.getId(), foundBook.getId());
        assertEquals(book.getName(), foundBook.getName());
        assertEquals(book.getAuthor(), foundBook.getAuthor());
        assertEquals(book.getBookStatus(), foundBook.getBookStatus());
        assertEquals(book.getBookType(), foundBook.getBookType());
        assertEquals(book.getAnnotation(), foundBook.getAnnotation());
        assertEquals(book.getStartedReadDate(), foundBook.getStartedReadDate());
        assertEquals(book.getEndedReadDate(), foundBook.getEndedReadDate());
        assertEquals(book.getGenres(), foundBook.getGenres());
    }

    @Test
    void deleteBookWorks() {
        Optional<Book> foundBook = bookRepository.findById(book.getId());

        assertTrue(foundBook.isPresent());

        bookRepository.deleteById(foundBook.get().getId());

        Optional<Book> deletedBook = bookRepository.findById(book.getId());

        assertTrue(deletedBook.isEmpty());
    }

    @Test
    void updateBookWorks() {
        Book bookUpdate = new Book();

        bookUpdate.setName("updated book");
        bookUpdate.setAnnotation("updated annotation");

        Optional<Book> optionalBook = bookRepository.updateById(book.getId(), bookUpdate);

        assertTrue(optionalBook.isPresent());

        Book updatedBook = optionalBook.get();

        assertEquals(book.getId(), updatedBook.getId());
        assertEquals(bookUpdate.getName(), updatedBook.getName());
        assertEquals(book.getAuthor(), updatedBook.getAuthor());
        assertEquals(book.getBookStatus(), updatedBook.getBookStatus());
        assertEquals(book.getBookType(), updatedBook.getBookType());
        assertEquals(bookUpdate.getAnnotation(), updatedBook.getAnnotation());
        assertEquals(book.getStartedReadDate(), updatedBook.getStartedReadDate());
        assertEquals(book.getEndedReadDate(), updatedBook.getEndedReadDate());
        assertEquals(book.getGenres(), updatedBook.getGenres());
    }
}
