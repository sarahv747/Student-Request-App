package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.base.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
}
