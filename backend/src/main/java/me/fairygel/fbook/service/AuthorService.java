package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dao.AuthorDAO;
import me.fairygel.fbook.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorDAO authorDAO;

    public void create(Author author) {
        authorDAO.create(author);
    }

    public Author read(Long id) {
        return authorDAO.read(id);
    }

    public Author update(Long id, Author author) {
        return authorDAO.update(id, author);
    }

    public void delete(Long id) {
        authorDAO.delete(id);
    }

    public List<Author> index() {
        return authorDAO.index();
    }

}
