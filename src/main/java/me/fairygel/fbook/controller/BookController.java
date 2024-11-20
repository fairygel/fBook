package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.service.AuthorService;
import me.fairygel.fbook.service.BookService;
import me.fairygel.fbook.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    private static final String REDIRECT_TO_INDEX = "redirect:/";

    @PostMapping(value = {"", "/"})
    public String create(@ModelAttribute CreateBookDTO bookDTO) {
        bookService.create(bookDTO);
        return REDIRECT_TO_INDEX;
    }
    @GetMapping("/new")
    public String newBookFrom(Model model) {
        // main model
        model.addAttribute("createBookDTO", new CreateBookDTO());

        // dependencies
        model.addAttribute("authors", authorService.index());
        model.addAttribute("genres", genreService.index());

        return "book/new-book";
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public String read(@PathVariable Long id, Model model) {
        BookFullViewDTO bookDTO = bookService.read(id);

        if (bookDTO == null) {return REDIRECT_TO_INDEX;}

        model.addAttribute("bookFullView", bookDTO);

        return "book/book-view";
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public String update(@PathVariable Long id, @ModelAttribute UpdateBookDTO bookDTO, Model model) {
        BookFullViewDTO updatedBookDTO = bookService.update(id, bookDTO);
        model.addAttribute("bookFullView", updatedBookDTO);
        return "book/book-view";
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return REDIRECT_TO_INDEX;
    }
    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        List<IndexBookViewDTO> bookDTOs = bookService.index();
        model.addAttribute("indexBookView", bookDTOs);
        return "book/index";
    }
}
