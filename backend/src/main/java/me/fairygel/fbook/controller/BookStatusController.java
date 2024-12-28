package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.status.BookStatusDTO;
import me.fairygel.fbook.dto.book.status.BookStatusIndexViewDTO;
import me.fairygel.fbook.service.BookStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book-statuses")
public class BookStatusController {
    private final BookStatusService bookStatusService;

    @GetMapping(value = {"/{id}", "/{id}/"})
    public BookStatusDTO read(@PathVariable Short id) {
        return bookStatusService.read(id);
    }

    @GetMapping(value = {"/", ""})
    public Set<BookStatusIndexViewDTO> index() {
        return bookStatusService.index();
    }
}
