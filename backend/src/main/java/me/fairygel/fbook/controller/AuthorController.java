package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.author.AuthorDTO;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping(value = {"/", ""})
    public void create(@RequestBody AuthorDTO authorDTO) {
        authorService.create(authorDTO);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public AuthorDTO read(@PathVariable Long id) {
        return authorService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public AuthorDTO update(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        return authorService.update(id, authorDTO);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
    @GetMapping(value = {"/", ""})
    public Set<AuthorIndexViewDTO> index() {
        return authorService.index();
    }
}
