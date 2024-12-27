package me.fairygel.fbook.mapper;

import lombok.Setter;
import me.fairygel.fbook.dto.grade.CreateGradeDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
import me.fairygel.fbook.dto.grade.UpdateGradeDTO;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.repository.BookCrudRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class GradeMapper {
    @Setter(onMethod_ = @Autowired)
    private BookCrudRepository bookRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", expression = "java(getBookFromRepository(createGradeDTO.getBookId()))")
    public abstract Grade createGradeDtoToGrade(CreateGradeDTO createGradeDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true)
    public abstract Grade updateGradeDtoToGrade(UpdateGradeDTO updateGradeDTO);

    @Mapping(target = "bookName", expression = "java(grade.getBook().getName())")
    @Mapping(target = "bookId", expression = "java(grade.getBook().getId())")
    public abstract GradePreviewDTO gradeToGradePreviewDto(Grade grade);

    public abstract Set<GradePreviewDTO> gradesToGradePreviews(Set<Grade> grades);

    private Book getBookFromRepository(Long id) {
        return bookRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
