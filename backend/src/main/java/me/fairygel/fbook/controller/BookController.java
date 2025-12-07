package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.*;
import me.fairygel.fbook.service.BookCoverService;
import me.fairygel.fbook.service.BookService;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookCoverService coverService;

    @PostMapping(value = {"", "/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public IndexBookViewDTO create(@RequestPart("book") @Validated(OnCreateGroup.class) BookDTO bookDTO,
                       @RequestPart(value = "cover", required = false) MultipartFile cover) {
        return bookService.create(bookDTO, cover);
    }
    @GetMapping(value = {"/{id}/", "/{id}"})
    public BookFullViewDTO read(@PathVariable Long id) {
        return bookService.read(id);
    }
    @GetMapping(value = {"/{id}/cover", "/{id}/cover/"})
    public ResponseEntity<byte[]> getCover(@PathVariable Long id) {
        BookCoverDTO cover = coverService.getCover(id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(cover.getImageType()))
                .body(cover.getCover());
    }
    @PatchMapping(value = {"/{id}/", "/{id}"})
    public BookFullViewDTO update(@PathVariable Long id,
                                  @RequestPart("book") @Validated(OnUpdateGroup.class) BookDTO bookDTO,
                                  @RequestPart(value = "cover", required = false) MultipartFile cover) {
        return bookService.update(id, bookDTO, cover);
    }
    @DeleteMapping(value = {"/{id}/", "/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        coverService.deleteCover(id);
        bookService.delete(id);
    }
    @DeleteMapping(value = {"/{id}/cover/", "/{id}/cover"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCover(@PathVariable Long id) {
        coverService.deleteCover(id);
    }
    @GetMapping(value = {"", "/"})
    public Set<IndexBookViewDTO> index() {
        return bookService.index();
    }
}
