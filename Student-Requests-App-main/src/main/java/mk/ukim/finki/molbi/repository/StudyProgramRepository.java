package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.base.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, String> {
}
