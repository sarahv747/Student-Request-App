package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.base.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}
