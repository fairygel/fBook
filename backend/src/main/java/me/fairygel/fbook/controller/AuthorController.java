package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.service.AuthorService;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;
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
    public void create(@RequestBody @Validated(OnCreateGroup.class) AuthorIndexViewDTO authorDTO) {
        authorService.create(authorDTO);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public AuthorIndexViewDTO read(@PathVariable Long id) {
        return authorService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public AuthorIndexViewDTO update(@PathVariable Long id, @RequestBody @Validated(OnUpdateGroup.class) AuthorIndexViewDTO authorDTO) {
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
