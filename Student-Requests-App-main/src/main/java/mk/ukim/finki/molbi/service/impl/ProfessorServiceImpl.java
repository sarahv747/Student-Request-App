package mk.ukim.finki.molbi.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.base.Professor;
import mk.ukim.finki.molbi.model.exceptions.ProfessorNotFoundException;
import mk.ukim.finki.molbi.repository.ProfessorRepository;
import mk.ukim.finki.molbi.service.ProfessorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;

    @Override
    public List<Professor> listAll() {
        return professorRepository.findAll();
    }

    @Override
    public Professor findById(String id) {
        return professorRepository.findById(id).orElseThrow(() -> new ProfessorNotFoundException(id));
    }
}
