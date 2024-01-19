package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.requests.RequestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestSessionRepository extends JpaRepository<RequestSession, Long> {
}
