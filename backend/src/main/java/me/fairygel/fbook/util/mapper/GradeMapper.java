package me.fairygel.fbook.util.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.Setter;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
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

    @Mapping(target = "book", expression =
            "java(mapBook(grade.getBook()))")
    public abstract GradePreviewDTO gradeToGradePreviewDto(Grade grade);

    public abstract Set<GradePreviewDTO> gradesToGradePreviews(Set<Grade> grades);

    protected Book getBookFromRepository(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No book with id = " + id));
    }

    protected IndexBookViewDTO mapBook(Book book) {
        IndexBookViewDTO bookDTO = new IndexBookViewDTO();

        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());

        return bookDTO;
    }
}
