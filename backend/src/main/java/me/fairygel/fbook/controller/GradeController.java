package me.fairygel.fbook.controller;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService gradeService;

    @PostMapping(value = {"/", ""})
    public void create(@RequestBody Grade grade) {
        gradeService.create(grade);
    }

    @GetMapping(value = {"/{id}/", "/{id}"})
    public Grade read(@PathVariable Long id) {
        return gradeService.read(id);
    }

    @PatchMapping(value = {"/{id}/", "/{id}"})
    public Grade update(@PathVariable Long id, @RequestBody Grade grade) {
        return gradeService.update(id, grade);
    }

    @DeleteMapping(value = {"/{id}/", "/{id}"})
    public void delete(@PathVariable Long id) {
        gradeService.delete(id);
    }

    @GetMapping(value = {"/", ""})
    public List<Grade> index() {
        return gradeService.index();
    }
}
