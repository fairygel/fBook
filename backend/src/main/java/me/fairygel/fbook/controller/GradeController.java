package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.grade.CreateGradeDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
import me.fairygel.fbook.dto.grade.UpdateGradeDTO;
import me.fairygel.fbook.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService gradeService;

    @PostMapping(value = {"/", ""})
    public void create(@RequestBody CreateGradeDTO gradeDTO) {
        gradeService.create(gradeDTO);
    }

    @GetMapping(value = {"/{id}/", "/{id}"})
    public GradePreviewDTO read(@PathVariable Long id) {
        return gradeService.read(id);
    }

    @PatchMapping(value = {"/{id}/", "/{id}"})
    public GradePreviewDTO update(@PathVariable Long id, @RequestBody UpdateGradeDTO gradeDTO) {
        return gradeService.update(id, gradeDTO);
    }

    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public void delete(@PathVariable Long id) {
        gradeService.delete(id);
    }

    @GetMapping(value = {"/", ""})
    public Set<GradePreviewDTO> index() {
        return gradeService.index();
    }
}
