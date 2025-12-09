package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.GenreDTO;
import me.fairygel.fbook.service.GenreService;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping(value = {"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO create(@RequestBody @Validated(OnCreateGroup.class) GenreDTO genreDTO) {
        return genreService.create(genreDTO);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public GenreDTO read(@PathVariable Long id) {
        return genreService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public GenreDTO update(@PathVariable Long id, @RequestBody @Validated(OnUpdateGroup.class) GenreDTO genreDTO) {
        return genreService.update(id, genreDTO);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
    @GetMapping(value = {"", "/"})
    public Set<GenreDTO> index() {
         return genreService.index();
    }
}
