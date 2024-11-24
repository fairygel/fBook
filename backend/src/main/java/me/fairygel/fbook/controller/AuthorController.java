package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping(value = {"/", ""})
    public void create(@RequestBody Author author) {
        authorService.create(author);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public Author read(@PathVariable Long id) {
        return authorService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public Author update(@PathVariable Long id, @RequestBody Author author) {
        return authorService.update(id, author);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
    @GetMapping(value = {"/", ""})
    public List<Author> index() {
        return authorService.index();
    }
}
