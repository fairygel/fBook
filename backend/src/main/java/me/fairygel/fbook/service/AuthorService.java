package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.repository.AuthorCrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorCrudRepository authorRepository;

    public void create(Author author) {
        authorRepository.save(author);
    }

    public Author read(Long id) {
        return authorRepository
                .findById(id).orElse(
                        authorRepository.findById(0L)
                                .orElseThrow(IllegalStateException::new)
                );
    }

    public Author update(Long id, Author author) {
        return authorRepository.updateById(id, author).orElseThrow(IllegalAccessError::new);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Author> index() {
        List<Author> authors = new ArrayList<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }
}
