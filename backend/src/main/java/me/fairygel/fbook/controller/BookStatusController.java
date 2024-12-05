package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.BookStatus;
import me.fairygel.fbook.service.BookStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book-statuses")
public class BookStatusController {
    private final BookStatusService bookStatusService;

    @GetMapping(value = {"/{id}", "/{id}/"})
    public BookStatus read(@PathVariable Long id) {
        return bookStatusService.read(id);
    }

    @GetMapping(value = {"/", ""})
    public List<BookStatus> index() {
        return bookStatusService.index();
    }
}
