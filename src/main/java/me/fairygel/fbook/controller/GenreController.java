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
public class GenreController {
    private final GenreService genreService;

    private static final String REDIRECT_TO_INDEX = "redirect:/genres";
    private static final String GENRE_ATTRIBUTE = "genre";

    @PostMapping(value = {"/genres", "/genres/"})
    public String create(@ModelAttribute Genre genre) {
        genreService.create(genre);
        return REDIRECT_TO_INDEX;
    }
    @GetMapping(value = {"/genres/create", "/genres/create/"})
    public String newBookFrom(Model model) {
        model.addAttribute(GENRE_ATTRIBUTE, new Genre());
        return "genre/create-genre";
    }
    @GetMapping(value = {"/genres/{id}/", "/genres/{id}"})
    public String read(@PathVariable Long id, Model model) {
        Genre genre = genreService.read(id);

        if (genre == null) {return REDIRECT_TO_INDEX;}

        model.addAttribute(GENRE_ATTRIBUTE, genre);
        return "genre/read-genre";
    }
    @PatchMapping(value = {"/genres/{id}/", "/genres/{id}"})
    public String update(@PathVariable Long id, @ModelAttribute Genre genre, Model model) {
        Genre updatedGenre = genreService.update(id, genre);
        model.addAttribute(GENRE_ATTRIBUTE, updatedGenre);
        return "genre/read-genre";
    }
    @DeleteMapping(value = {"/genres/{id}/", "/genres/{id}"})
    public String delete(@PathVariable Long id) {
        genreService.delete(id);
        return REDIRECT_TO_INDEX;
    }
    @GetMapping(value = {"/genres", "/genres/"})
    public String index(Model model) {
        List<Genre> genres = genreService.index();
        model.addAttribute("genres", genres);
        return "genre/index";
    }
}
