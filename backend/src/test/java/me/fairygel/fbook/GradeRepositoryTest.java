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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Testcontainers
class GradeRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.15-alpine");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    GradeCrudRepository gradeRepository;

    @Autowired
    BookCrudRepository bookRepository;
    @Autowired
    AuthorCrudRepository authorRepository;
    @Autowired
    BookStatusReadOnlyRepository bookStatusRepository;
    @Autowired
    BookTypeReadOnlyRepository bookTypeRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    Grade grade;

    @BeforeEach
    void createGrade() {
        Book book = new Book();

        book.setName("book");

        Optional<Author> author = authorRepository.findById(0L);
        Optional<BookStatus> bookStatus = bookStatusRepository.findById((short) 0);
        Optional<BookType> bookType = bookTypeRepository.findById((short) 0);

        assertTrue(author.isPresent());
        assertTrue(bookStatus.isPresent());
        assertTrue(bookType.isPresent());

        book.setAuthor(author.get());
        book.setBookStatus(bookStatus.get());
        book.setBookType(bookType.get());

        bookRepository.save(book);

        grade = new Grade();

        grade.setBook(book);
        grade.setRating((short) 5);
        grade.setComment("comment");

        gradeRepository.save(grade);
    }

    @Test
    void errorWhenSavingGradeWithoutBook() {
        Grade problemGrade = new Grade();

        problemGrade.setRating((short) 5);

        assertThrows(DataIntegrityViolationException.class, () -> gradeRepository.save(problemGrade));
    }

    @Test
    void errorWhenSavingGradeWithoutRating() {
        Grade problemGrade = new Grade();

        problemGrade.setBook(grade.getBook());

        assertThrows(DataIntegrityViolationException.class, () -> gradeRepository.save(problemGrade));
    }

    @Test
    void findByIdWorksProperly() {
        Optional<Grade> optionalFoundGrade = gradeRepository.findById(grade.getId());

        assertTrue(optionalFoundGrade.isPresent());

        assertEquals(optionalFoundGrade.get().getId(), grade.getId());
        assertEquals(optionalFoundGrade.get().getBook(), grade.getBook());
        assertEquals(optionalFoundGrade.get().getComment(), grade.getComment());
        assertEquals(optionalFoundGrade.get().getRating(), grade.getRating());
    }

    @Test
    void deleteGradeWorksCorrectly() {
        Optional<Grade> gradeOptional = gradeRepository.findById(grade.getId());

        assertTrue(gradeOptional.isPresent());

        gradeRepository.deleteById(gradeOptional.get().getId());

        Optional<Grade> deletedGrade = gradeRepository.findById(gradeOptional.get().getId());

        assertTrue(deletedGrade.isEmpty());
    }

    @Test
    void updateGradeWorksCorrectly() {
        Grade gradeUpdate = new Grade();

        gradeUpdate.setRating((short) 0);

        Optional<Grade> gradeOptional = gradeRepository.updateById(grade.getId(), gradeUpdate);

        assertTrue(gradeOptional.isPresent());

        Grade updatedGrade = gradeOptional.get();

        assertEquals(grade.getId(), updatedGrade.getId());
        assertEquals(gradeUpdate.getRating(), updatedGrade.getRating());
        assertEquals(grade.getComment(), updatedGrade.getComment());
        assertEquals(grade.getBook(), updatedGrade.getBook());
    }
}
