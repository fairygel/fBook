package me.fairygel.fbook.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping(value = {"", "/"})
    public void create(@RequestBody @Valid CreateBookDTO bookDTO) {
        bookService.create(bookDTO);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public BookFullViewDTO read(@PathVariable Long id) {
        return bookService.read(id);
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public BookFullViewDTO update(@PathVariable Long id, @RequestBody @Valid UpdateBookDTO bookDTO) {
        return bookService.update(id, bookDTO);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
    @GetMapping(value = {"", "/"})
    public Set<IndexBookViewDTO> index() {
        return bookService.index();
    }
}
