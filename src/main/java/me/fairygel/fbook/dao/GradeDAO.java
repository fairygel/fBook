package me.fairygel.fbook.dao;

import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.util.PropertyMerger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GradeDAO {
    private final List<Grade> grades = new ArrayList<>();

    public void create(Grade grade) {
        grades.add(grade);
    }

    public Grade read(long id) {
        return grades.stream().filter(g -> g.getId() == id).findAny().orElse(null);
    }

    public Grade update(long id, Grade grade) {
        Grade existingGrade = read(id);

        PropertyMerger.merge(grade, existingGrade);

        return existingGrade;
    }

    public void delete(long id) {
        grades.removeIf(g -> g.getId() == id);
    }

    public List<Grade> index() {
        return grades;
    }
}
