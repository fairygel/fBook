package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.type.BookTypeDTO;
import me.fairygel.fbook.dto.book.type.BookTypeIndexViewDTO;
import me.fairygel.fbook.entity.BookType;
import me.fairygel.fbook.service.BookTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book-types")
public class BookTypeController {
    private final BookTypeService bookTypeService;

    @GetMapping(value = {"/{id}", "/{id}/"})
    public BookTypeDTO read(@PathVariable Short id) {
        return bookTypeService.read(id);
    }
    @GetMapping(value = {"/", ""})
    public Set<BookTypeIndexViewDTO> index() {
        return bookTypeService.index();
    }
}
