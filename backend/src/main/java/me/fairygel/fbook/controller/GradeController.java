package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.grade.GradeDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
import me.fairygel.fbook.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService gradeService;

    @PostMapping(value = {"/", ""})
    @ResponseStatus(HttpStatus.CREATED)
    public GradePreviewDTO create(@RequestBody @Validated(OnCreateGroup.class) GradeDTO gradeDTO) {
        return gradeService.create(gradeDTO);
    }

    @GetMapping(value = {"/{id}/", "/{id}"})
    public GradePreviewDTO read(@PathVariable Long id) {
        return gradeService.read(id);
    }

    @PatchMapping(value = {"/{id}/", "/{id}"})
    public GradePreviewDTO update(@PathVariable Long id, @RequestBody @Validated(OnUpdateGroup.class) GradeDTO gradeDTO) {
        return gradeService.update(id, gradeDTO);
    }

    @DeleteMapping(value = {"/{id}/", "/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        gradeService.delete(id);
    }

    @GetMapping(value = {"/", ""})
    public Set<GradePreviewDTO> index() {
        return gradeService.index();
    }
}
