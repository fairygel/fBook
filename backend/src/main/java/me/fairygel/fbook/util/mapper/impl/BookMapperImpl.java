package me.fairygel.fbook.util.mapper.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.dto.book.*;
import me.fairygel.fbook.dto.BookStatusDTO;
import me.fairygel.fbook.dto.BookTypeDTO;
import me.fairygel.fbook.dto.GenreDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
import me.fairygel.fbook.entity.*;
import me.fairygel.fbook.util.mapper.*;
import me.fairygel.fbook.repository.AuthorCrudRepository;
import me.fairygel.fbook.repository.BookStatusReadOnlyRepository;
import me.fairygel.fbook.repository.BookTypeReadOnlyRepository;
import me.fairygel.fbook.repository.GenreCrudRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class BookMapperImpl implements BookMapper {
	private final AuthorMapper authorMapper;
	private final GenreMapper genreMapper;
	private final BookStatusMapper bookStatusMapper;
	private final BookTypeMapper bookTypeMapper;
	private final GradeMapper gradeMapper;

	private AuthorCrudRepository authorRepository;
	private BookStatusReadOnlyRepository bookStatusRepository;
	private BookTypeReadOnlyRepository bookTypeRepository;
	private GenreCrudRepository genreRepository;

	// --- DTO To Book ---

	@Override
	public Book bookDtoToBook (BookDTO bookDTO, Boolean isCreating) {
		Book book = new Book();

		Author author = getAuthor(bookDTO.getAuthorId(), isCreating);
		Set<Genre> genres = getGenres(bookDTO.getGenreIds(), isCreating);
		BookStatus bookStatus = getBookStatus(bookDTO.getBookStatusId(), isCreating);
		BookType bookType = getBookType(bookDTO.getBookTypeId(), isCreating);

		book.setName(bookDTO.getName());
		book.setAuthor(author);
		book.setGenres(genres);
		book.setBookStatus(bookStatus);
		book.setStartedReadDate(bookDTO.getStartedReadDate());
		book.setEndedReadDate(bookDTO.getEndedReadDate());
		book.setAnnotation(bookDTO.getAnnotation());
		book.setBookType(bookType);

		return book;
	}

	// --- Book To DTO ---

	@Override
	public BookFullViewDTO bookToBookFullViewDto (Book book) {
		BookFullViewDTO bookDTO = new BookFullViewDTO();

		Grade bookGrade = getSingleGrade(book.getGrades());

		AuthorIndexViewDTO authorDTO = authorMapper.authorToAuthorIndexDto(book.getAuthor());
		Set<GenreDTO> genreDTOs = genreMapper.genresToIndex(book.getGenres());
		BookStatusDTO bookStatusDTO = bookStatusMapper.bookStatusToBookStatusDto(book.getBookStatus());
		BookTypeDTO bookTypeDTO = bookTypeMapper.bookTypeToBookTypeDto(book.getBookType());
		GradePreviewDTO gradeDTO = gradeMapper.gradeToGradePreviewDTO(bookGrade);

		bookDTO.setId(book.getId());
		bookDTO.setName(book.getName());
		bookDTO.setAuthor(authorDTO);
		bookDTO.setGenres(genreDTOs);
		bookDTO.setBookStatus(bookStatusDTO);
		bookDTO.setBookType(bookTypeDTO);
		bookDTO.setStartedReadDate(dateToString(book.getStartedReadDate()));
		bookDTO.setEndedReadDate(dateToString(book.getEndedReadDate()));
		bookDTO.setAnnotation(book.getAnnotation());
		bookDTO.setGrade(gradeDTO);

		return bookDTO;
	}

	public BookCoverDTO bookToBookCoverDto (Book book) {
		BookCoverDTO bookDTO = new BookCoverDTO();

		//bookDTO.setCover(book.getCover());

		return bookDTO;
	}

	@Override
	public IndexBookViewDTO bookToIndexBookViewDto (Book book) {
		IndexBookViewDTO bookDTO = new IndexBookViewDTO();

		bookDTO.setId(book.getId());
		bookDTO.setName(book.getName());

		return bookDTO;
	}

	// --- Helpful Stuff ---
	private Grade getSingleGrade (Set<Grade> grades) {
		if ( grades.isEmpty() ) return null;

		return grades.iterator().next();
	}

	private Author getAuthor (Long authorId, Boolean zeroOnNull) {
		if ( authorId == null && zeroOnNull ) return authorRepository.findById(0L)
				.orElseThrow(() -> new EntityNotFoundException("No author with ID = 0"));
		else if ( authorId == null ) return null;

		return authorRepository.findById(authorId)
				.orElseThrow(() -> new EntityNotFoundException("No author with ID = " + authorId));
	}

	private Set<Genre> getGenres (Set<Long> genreIds, Boolean zeroOnNull) {
		if ( genreIds == null && zeroOnNull ) return genreRepository.findById(0L)
                .map(Collections::singleton)
                .orElseThrow(() -> new EntityNotFoundException("No genre with ID = 0"));
		else if ( genreIds == null ) return null;

		Set<Genre> genres = new HashSet<>();

		for ( Long genreId : genreIds ) {
			Genre genre = genreRepository.findById(genreId)
					.orElseThrow(() -> new EntityNotFoundException("No genre with ID = " + genreId));
			genres.add(genre);
		}

		if ( genres.size() > 1 ) {
			genres.removeIf(g -> g.getId() == 0);
		}

		return genres;
	}

	private BookStatus getBookStatus (Short statusId, Boolean zeroOnNull) {
		if ( statusId == null && zeroOnNull ) return bookStatusRepository.findById((short) 0)
				.orElseThrow(() -> new EntityNotFoundException("No book status with ID = 0"));
		else if (statusId == null) return null;

		return bookStatusRepository.findById(statusId)
				.orElseThrow(() -> new EntityNotFoundException("No book status with ID = " + statusId));
	}

	private BookType getBookType (Short typeId, Boolean zeroOnNull) {
		if ( typeId == null && zeroOnNull ) return bookTypeRepository.findById((short) 0)
				.orElseThrow(() -> new EntityNotFoundException("No book status with ID = 0"));
		else if (typeId == null) return null;

		return bookTypeRepository.findById(typeId)
				.orElseThrow(() -> new EntityNotFoundException("No book type with ID = " + typeId));
	}

	private String dateToString (LocalDate date) {
		if ( date == null ) return "";
		return date.toString();
	}
}
