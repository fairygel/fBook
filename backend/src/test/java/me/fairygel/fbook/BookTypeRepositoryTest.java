package me.fairygel.fbook;

import me.fairygel.fbook.repository.BookTypeReadOnlyRepository;
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
class BookTypeRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.15-alpine");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

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

    @Test
    void haveBookStatuses() {
        assertEquals(5, bookTypeRepository.count());
    }

    @Test
    void canReadAllBookStatuses() {
        assertTrue(bookTypeRepository.findById((short) 0).isPresent());
        assertTrue(bookTypeRepository.findById((short) 1).isPresent());
        assertTrue(bookTypeRepository.findById((short) 2).isPresent());
        assertTrue(bookTypeRepository.findById((short) 3).isPresent());
        assertTrue(bookTypeRepository.findById((short) 4).isPresent());
    }
}
