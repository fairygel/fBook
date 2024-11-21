package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    private static final String REDIRECT_TO_INDEX = "redirect:/authors";
    private static final String AUTHOR_ATTRIBUTE = "author";

    @PostMapping(value = {"/authors", "/authors/"})
    public String create(@ModelAttribute Author author) {
        authorService.create(author);
        return REDIRECT_TO_INDEX;
    }
    @GetMapping(value = {"/authors/create", "/authors/create/"})
    public String newBookFrom(Model model) {
        model.addAttribute(AUTHOR_ATTRIBUTE, new Author());
        return "author/create-author";
    }
    @GetMapping(value = {"/authors/{id}/", "/authors/{id}"})
    public String read(@PathVariable Long id, Model model) {
        Author author = authorService.read(id);

        if (author == null) {return REDIRECT_TO_INDEX;}

        model.addAttribute(AUTHOR_ATTRIBUTE, author);
        return "author/read-author";
    }
    @PatchMapping(value = {"/authors/{id}/", "/authors/{id}"})
    public String update(@PathVariable Long id, @ModelAttribute Author author, Model model) {
        Author updatedAuthor = authorService.update(id, author);
        model.addAttribute(AUTHOR_ATTRIBUTE, updatedAuthor);
        return "author/read-author";
    }
    @DeleteMapping(value = {"/authors/{id}/", "/authors/{id}"})
    public String delete(@PathVariable Long id) {
        authorService.delete(id);
        return REDIRECT_TO_INDEX;
    }
    @GetMapping(value = {"/authors", "/authors/"})
    public String index(Model model) {
        List<Author> authors = authorService.index();
        model.addAttribute("authors", authors);
        return "author/index";
    }
}
