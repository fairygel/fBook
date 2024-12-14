package me.fairygel.fbook;

import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.repository.AuthorCrudRepository;
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
class AuthorRepositoryTest {

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.15-alpine");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	AuthorCrudRepository authorRepository;
	Author author;

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
	void saveAuthorWorks() {
		author = new Author();

        author.setFirstName("John");
        author.setLastName("Doe");

        Author savedAuthor = authorRepository.save(author);

		assertEquals(author.getId(), savedAuthor.getId());
		assertEquals(author.getFirstName(), savedAuthor.getFirstName());
		assertEquals(author.getLastName(), savedAuthor.getLastName());
	}

	@Test
	void errorWhenSavingWrongAuthor() {
		Author wrongAuthor = new Author();

		assertThrows(DataIntegrityViolationException.class, () -> authorRepository.save(wrongAuthor));
	}

	@Test
    void shouldDeleteAuthorCorrectly_findByIdWorksProperly() {
        Optional<Author> foundAuthor = authorRepository.findById(author.getId());

		assertTrue(foundAuthor.isPresent());

		assertEquals(author.getId(), foundAuthor.get().getId());
		assertEquals(author.getFirstName(), foundAuthor.get().getFirstName());
		assertEquals(author.getLastName(), foundAuthor.get().getLastName());

		authorRepository.deleteById(author.getId());

		Optional<Author> deletedAuthor = authorRepository.findById(author.getId());
		assertTrue(deletedAuthor.isEmpty());
	}

	@Test
	void shouldUpdateProperly() {
		Author newAuthor = new Author();

		newAuthor.setFirstName("new name");

		Optional<Author> updatedAuthor = authorRepository.updateById(author.getId(), newAuthor);

		assertTrue(updatedAuthor.isPresent());

		assertEquals(author.getId(), updatedAuthor.get().getId());
		assertEquals(newAuthor.getFirstName(), updatedAuthor.get().getFirstName());
		assertEquals(author.getLastName(), updatedAuthor.get().getLastName());
	}

	@Test
	void emptyAuthorShouldExist() {
		Optional<Author> emptyAuthor = authorRepository.findById(0L);

		assertTrue(emptyAuthor.isPresent());
	}
}
