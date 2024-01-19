package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.base.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
}
