package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.base.StudyProgram;

import java.util.List;

public interface StudyProgramService {
    List<StudyProgram> listAll();

    StudyProgram findByCode(String code);
}
