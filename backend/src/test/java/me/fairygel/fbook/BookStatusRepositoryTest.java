package me.fairygel.fbook;

import me.fairygel.fbook.repository.BookStatusReadOnlyRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@Testcontainers
class BookStatusRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.15-alpine");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    BookStatusReadOnlyRepository bookStatusRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    void haveBookStatuses() {
        assertEquals(6, bookStatusRepository.count());
    }

    @Test
    void canReadAllBookStatuses() {
        assertTrue(bookStatusRepository.findById((short) 0).isPresent());
        assertTrue(bookStatusRepository.findById((short) 1).isPresent());
        assertTrue(bookStatusRepository.findById((short) 2).isPresent());
        assertTrue(bookStatusRepository.findById((short) 3).isPresent());
        assertTrue(bookStatusRepository.findById((short) 4).isPresent());
        assertTrue(bookStatusRepository.findById((short) 5).isPresent());
    }
}
