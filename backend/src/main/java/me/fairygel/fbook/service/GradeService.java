package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.grade.GradeDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.repository.GradeCrudRepository;
import me.fairygel.fbook.util.mapper.GradeMapperImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class GradeService {
    private final GradeCrudRepository gradeCrudRepository;
    private final GradeMapperImpl mapper;

    public GradePreviewDTO create(GradeDTO gradeDTO) {
        Grade grade = mapper.gradeDtoToGrade(gradeDTO, true);
        Grade savedGrade = gradeCrudRepository.save(grade);
        return mapper.gradeToGradePreviewDTO(savedGrade);
    }

    public GradePreviewDTO read(Long id) {
        Grade grade = gradeCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No grade with ID = " + id));
        return mapper.gradeToGradePreviewDTO(grade);
    }

    public GradePreviewDTO update(Long id, GradeDTO gradeDTO) {
        Grade grade = mapper.gradeDtoToGrade(gradeDTO, false);

        Grade updatedGrade = gradeCrudRepository.updateById(id, grade)
                .orElseThrow(() -> new EntityNotFoundException("No grade with ID = " + id));

        return mapper.gradeToGradePreviewDTO(updatedGrade);
    }

    public void delete(Long id) {
        gradeCrudRepository.deleteById(id);
    }

    public Set<GradePreviewDTO> index() {
        Set<Grade> grades = new HashSet<>();
        gradeCrudRepository.findAll().forEach(grades::add);

        return mapper.gradesToGradePreviewDTOs(grades);
    }
}
