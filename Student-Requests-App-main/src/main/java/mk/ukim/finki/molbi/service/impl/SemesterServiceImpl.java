package mk.ukim.finki.molbi.service.impl;

import mk.ukim.finki.molbi.model.base.Semester;
import mk.ukim.finki.molbi.repository.SemesterRepository;
import mk.ukim.finki.molbi.service.SemesterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository repository;

    public SemesterServiceImpl(SemesterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Semester> findAll() {
        return repository.findAll();
    }


    @Override
    public Semester findById(String code) {
        return repository.findById(code).orElseThrow();
    }

}
