package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.grade.CreateGradeDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
import me.fairygel.fbook.dto.grade.UpdateGradeDTO;
import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.mapper.GradeMapper;
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

    public GradePreviewDTO read(Long id) {
        Grade grade = gradeCrudRepository
                .findById(id).orElseThrow(IllegalAccessError::new);
        return mapper.gradeToGradePreviewDto(grade);
    }

    public GradePreviewDTO update(Long id, UpdateGradeDTO updateGradeDTO) {
        Grade grade = mapper.updateGradeDtoToGrade(updateGradeDTO);

        Grade updatedGrade = gradeCrudRepository.updateById(id, grade).orElseThrow(IllegalAccessError::new);

        return mapper.gradeToGradePreviewDto(updatedGrade);
    }

    public void delete(Long id) {
        gradeCrudRepository.deleteById(id);
    }

    public Set<GradePreviewDTO> index() {
        Set<Grade> grades = new HashSet<>();
        gradeCrudRepository.findAll().forEach(grades::add);

        return mapper.gradesToGradePreviews(grades);
    }
}
