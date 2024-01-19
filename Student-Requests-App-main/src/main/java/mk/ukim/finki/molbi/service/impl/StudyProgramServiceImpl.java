package mk.ukim.finki.molbi.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.base.StudyProgram;
import mk.ukim.finki.molbi.model.exceptions.StudyProgramNotFoundException;
import mk.ukim.finki.molbi.repository.StudyProgramRepository;
import mk.ukim.finki.molbi.service.StudyProgramService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudyProgramServiceImpl implements StudyProgramService {
    private final StudyProgramRepository studyProgramRepository;

    @Override
    public List<StudyProgram> listAll() {
        return studyProgramRepository.findAll();
    }

    @Override
    public StudyProgram findByCode(String code) {
        return studyProgramRepository.findById(code).orElseThrow(() -> new StudyProgramNotFoundException(code));
    }
}
