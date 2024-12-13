package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.repository.GradeCrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GradeService {
    private final GradeCrudRepository gradeCrudRepository;

    public void create(Grade grade) {
        gradeCrudRepository.save(grade);
    }

    public Grade read(Long id) {
        return gradeCrudRepository
                .findById(id).orElseThrow(IllegalStateException::new);
    }

    public Grade update(Long id, Grade grade) {
        return gradeCrudRepository.updateById(id, grade).orElseThrow(IllegalAccessError::new);
    }

    public void delete(Long id) {
        gradeCrudRepository.deleteById(id);
    }

    public List<Grade> index() {
        List<Grade> grades = new ArrayList<>();
        gradeCrudRepository.findAll().forEach(grades::add);
        return grades;
    }
}
