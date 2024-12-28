package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.author.AuthorDTO;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.util.mapper.AuthorMapper;
import me.fairygel.fbook.repository.AuthorCrudRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorCrudRepository authorRepository;
    private final AuthorMapper mapper;

    public void create(AuthorDTO authorDTO) {
        Author author = mapper.authorDtoToAuthor(authorDTO);
        authorRepository.save(author);
    }

    // we will try to find author with id, then, if it doesn't exist, we will try to get empty author
    // if he also isn't exists, throw an error
    public AuthorDTO read(Long id) {
        Author author = authorRepository
                .findById(id).orElse(
                        authorRepository.findById(0L)
                                .orElseThrow(IllegalStateException::new)
                );
        return mapper.authorToAuthorDto(author);
    }

    public AuthorDTO update(Long id, AuthorDTO authorDTO) {
        Author author = mapper.authorDtoToAuthor(authorDTO);

        Author updatedAuthor = authorRepository.updateById(id, author).orElseThrow(IllegalAccessError::new);

        return mapper.authorToAuthorDto(updatedAuthor);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    public Set<AuthorIndexViewDTO> index() {
        Set<Author> authors = new HashSet<>();
        authorRepository.findAll().forEach(authors::add);

        return mapper.authorsToIndexDto(authors);
    }
}
