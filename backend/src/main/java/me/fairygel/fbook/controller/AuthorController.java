package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.author.AuthorDTO;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.service.AuthorService;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping(value = {"/", ""})
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorIndexViewDTO create(@RequestBody @Validated(OnCreateGroup.class) AuthorDTO authorDTO) {
        return authorService.create(authorDTO);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public AuthorDTO read(@PathVariable Long id) {
        return authorService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public AuthorDTO update(@PathVariable Long id, @RequestBody @Validated(OnUpdateGroup.class) AuthorDTO authorDTO) {
        return authorService.update(id, authorDTO);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
    @GetMapping(value = {"/", ""})
    public Set<AuthorIndexViewDTO> index() {
        return authorService.index();
    }
}
