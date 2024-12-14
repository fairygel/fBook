package me.fairygel.fbook;

import me.fairygel.fbook.entity.Genre;
import me.fairygel.fbook.repository.GenreCrudRepository;
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
class GenreRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.15-alpine");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    GenreCrudRepository genreRepository;
    Genre genre;
    
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }


    @Test
    @BeforeEach
    void saveGenreWorks() {
        genre = new Genre();

        genre.setName("horror");

        Genre savedGenre = genreRepository.save(genre);

        assertEquals(genre.getId(), savedGenre.getId());
        assertEquals(genre.getName(), savedGenre.getName());
    }

    @Test
    void errorWhenSavingWrongGenre() {
        Genre wrongGenre = new Genre();

        assertThrows(DataIntegrityViolationException.class, () -> genreRepository.save(wrongGenre));
    }

    @Test
    void shouldDeleteGenreCorrectly_findByIdWorksProperly() {
        Optional<Genre> foundGenre = genreRepository.findById(genre.getId());

        assertTrue(foundGenre.isPresent());

        assertEquals(genre.getId(), foundGenre.get().getId());
        assertEquals(genre.getName(), foundGenre.get().getName());

        genreRepository.deleteById(genre.getId());

        Optional<Genre> deletedGenre = genreRepository.findById(genre.getId());
        assertTrue(deletedGenre.isEmpty());
    }

    @Test
    void shouldUpdateProperly() {
        Genre newGenre = new Genre();

        newGenre.setName("new genre");

        Optional<Genre> updatedGenre = genreRepository.updateById(genre.getId(), newGenre);

        assertTrue(updatedGenre.isPresent());

        assertEquals(genre.getId(), updatedGenre.get().getId());
        assertEquals(newGenre.getName(), updatedGenre.get().getName());
    }

    @Test
    void emptyGenreShouldExist() {
        Optional<Genre> emptyGenre = genreRepository.findById(0L);

        assertTrue(emptyGenre.isPresent());
    }
}
