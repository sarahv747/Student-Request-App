package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.base.Professor;

import java.util.List;

public interface ProfessorService {
    List<Professor> listAll();

    Professor findById(String id);
}
