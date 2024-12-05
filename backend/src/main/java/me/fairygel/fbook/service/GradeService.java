package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dao.GradeDAO;
import me.fairygel.fbook.entity.Grade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GradeService {
    private final GradeDAO gradeDAO;

    public void create(Grade grade) {
        gradeDAO.create(grade);
    }

    public Grade read(long id) {
        return gradeDAO.read(id);
    }

    public Grade update(long id, Grade grade) {
        return gradeDAO.update(id, grade);
    }

    public void delete(long id) {
        gradeDAO.delete(id);
    }

    public List<Grade> index() {
        return gradeDAO.index();
    }
}
