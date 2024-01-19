package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.base.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, String> {
}
