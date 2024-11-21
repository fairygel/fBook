package me.fairygel.fbook.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Base64;
import java.util.List;

@Getter
@Setter
public class Book {
    private Long id;
    private String name;
    // private Base64 cover;
    private Author author;
    private List<Genre> genres;
    private BookStatus bookStatus;
    private Date startedReadDate;
    private Date endedReadDate;
    private String annotation;
    private BookType bookType;
    private Grade grade;
}
