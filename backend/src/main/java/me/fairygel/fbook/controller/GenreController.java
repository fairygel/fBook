package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.Genre;
import me.fairygel.fbook.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping(value = {"", "/"})
    public void create(@RequestBody Genre genre) {
        genreService.create(genre);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public Genre read(@PathVariable Long id) {
        return genreService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public Genre update(@PathVariable Long id, @RequestBody Genre genre) {
        return genreService.update(id, genre);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
    @GetMapping(value = {"", "/"})
    public List<Genre> index() {
         return genreService.index();
    }
}
