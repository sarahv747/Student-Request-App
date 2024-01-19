package mk.ukim.finki.molbi.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.base.Subject;
import mk.ukim.finki.molbi.model.exceptions.SubjectNotFoundException;
import mk.ukim.finki.molbi.repository.SubjectRepository;
import mk.ukim.finki.molbi.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> listAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(String id) {
        return subjectRepository.findById(id).orElseThrow(() -> new SubjectNotFoundException(id));
    }

}