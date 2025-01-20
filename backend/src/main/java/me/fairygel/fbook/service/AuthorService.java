package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.author.AuthorDTO;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.repository.AuthorCrudRepository;
import me.fairygel.fbook.util.mapper.AuthorMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorCrudRepository authorRepository;
    private final AuthorMapper mapper;

    private static final String NO_AUTHOR = "No author with id = ";

    public void create(AuthorDTO authorDTO) {
        Author author = mapper.authorDtoToAuthor(authorDTO);
        authorRepository.save(author);
    }

    public AuthorDTO read(Long id) {
        if (id == 0L) throw new EntityNotFoundException(NO_AUTHOR + id);

        Author author = authorRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(NO_AUTHOR + id));
        return mapper.authorToAuthorDto(author);
    }

    public AuthorDTO update(Long id, AuthorDTO authorDTO) {
        if (id == 0L) throw new EntityNotFoundException(NO_AUTHOR + id);

        Author author = mapper.authorDtoToAuthor(authorDTO);

        Author updatedAuthor = authorRepository.updateById(id, author)
                .orElseThrow(() -> new EntityNotFoundException(NO_AUTHOR + id));

        return mapper.authorToAuthorDto(updatedAuthor);
    }

    public void delete(Long id) {
        if (id == 0L) throw new EntityNotFoundException(NO_AUTHOR + id);

        authorRepository.deleteById(id);
    }

    public Set<AuthorIndexViewDTO> index() {
        Set<Author> authors = new HashSet<>();
        authorRepository.findAll().forEach(a -> {if (a.getId() != 0L) authors.add(a);});

        return mapper.authorsToIndexDto(authors);
    }
}
