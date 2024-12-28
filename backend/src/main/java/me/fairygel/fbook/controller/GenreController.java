package me.fairygel.fbook.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.genre.GenreDTO;
import me.fairygel.fbook.dto.genre.GenreIndexViewDTO;
import me.fairygel.fbook.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping(value = {"", "/"})
    public void create(@RequestBody @Valid GenreDTO genreDTO) {
        genreService.create(genreDTO);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public GenreDTO read(@PathVariable Long id) {
        return genreService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public GenreDTO update(@PathVariable Long id, @RequestBody @Valid GenreDTO genreDTO) {
        return genreService.update(id, genreDTO);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
    @GetMapping(value = {"", "/"})
    public Set<GenreIndexViewDTO> index() {
         return genreService.index();
    }
}
