package me.fairygel.fbook.util.mapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.Setter;
import lombok.SneakyThrows;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.grade.GradeDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
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
    @Mapping(target = "book", expression = "java(getBookFromRepository(gradeDTO.getBookId(), isCreated))")
    public abstract Grade gradeDtoToGrade(GradeDTO gradeDTO, Boolean isCreated);

    @Mapping(target = "book", expression =
            "java(mapBook(grade.getBook()))")
    public abstract GradePreviewDTO gradeToGradePreviewDTO(Grade grade);

    public abstract Set<GradePreviewDTO> gradesToGradePreviewDTOs(Set<Grade> grades);

    @SneakyThrows
    protected Book getBookFromRepository(Long id, Boolean isCreated) {
        if ( !isCreated ) { return null; }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No book with ID = " + id));

        if (book.getGrades().isEmpty()) return book;
        else throw new ValidationException("Book with ID = " + id + " already have grade.");
    }

    protected IndexBookViewDTO mapBook(Book book) {
        IndexBookViewDTO bookDTO = new IndexBookViewDTO();

        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());

        return bookDTO;
    }
}
