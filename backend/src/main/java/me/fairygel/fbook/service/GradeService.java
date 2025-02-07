package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.grade.CreateGradeDTO;
import me.fairygel.fbook.dto.grade.GradeFullViewDTO;
import me.fairygel.fbook.dto.grade.UpdateGradeDTO;
import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.util.mapper.GradeMapper;
import me.fairygel.fbook.repository.GradeCrudRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class GradeService {
    private final GradeCrudRepository gradeCrudRepository;
    private final GradeMapper mapper;

    public void create(CreateGradeDTO gradeDTO) {
        Grade grade = mapper.createGradeDtoToGrade(gradeDTO);
        gradeCrudRepository.save(grade);
    }

    public GradeFullViewDTO read(Long id) {
        Grade grade = gradeCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No grade with id = " + id));
        return mapper.gradeToGradeFullViewDTO(grade);
    }

    public GradeFullViewDTO update(Long id, UpdateGradeDTO updateGradeDTO) {
        Grade grade = mapper.updateGradeDtoToGrade(updateGradeDTO);

        Grade updatedGrade = gradeCrudRepository.updateById(id, grade)
                .orElseThrow(() -> new EntityNotFoundException("No grade with id = " + id));

        return mapper.gradeToGradeFullViewDTO(updatedGrade);
    }

    public void delete(Long id) {
        gradeCrudRepository.deleteById(id);
    }

    public Set<GradeFullViewDTO> index() {
        Set<Grade> grades = new HashSet<>();
        gradeCrudRepository.findAll().forEach(grades::add);

        return mapper.gradesToGradeFullViews(grades);
    }
}
