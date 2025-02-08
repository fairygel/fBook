package me.fairygel.fbook.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.*;
import me.fairygel.fbook.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping(value = {"", "/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void create(@RequestPart("book") @Valid CreateBookDTO bookDTO,
                       @RequestPart(value = "cover", required = false) MultipartFile cover) {
        bookService.create(bookDTO, cover);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public BookFullViewDTO read(@PathVariable Long id) {
        return bookService.read(id);
    }
    @GetMapping(value = {"/{id}/cover", "/{id}/cover/"})
    public ResponseEntity<byte[]> getCover(@PathVariable Long id) {
        BookCoverDTO cover = bookService.getCover(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(cover.getCover());
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public BookFullViewDTO update(@PathVariable Long id,
                                  @RequestPart("book") @Valid UpdateBookDTO bookDTO,
                                  @RequestPart(value = "cover", required = false) MultipartFile cover) {
        return bookService.update(id, bookDTO, cover);
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
