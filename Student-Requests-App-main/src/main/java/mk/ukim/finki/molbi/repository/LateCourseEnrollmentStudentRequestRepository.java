package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.requests.LateCourseEnrollmentStudentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LateCourseEnrollmentStudentRequestRepository extends JpaRepository<LateCourseEnrollmentStudentRequest, Long> {

    Long countByRequestSession_Id(Long sessionId);

    Page<LateCourseEnrollmentStudentRequest> findByRequestSession_Id(Long requestSessionId, Pageable pageable);

    @Query("SELECT req FROM LateCourseEnrollmentStudentRequest req " +
            "WHERE (req.requestSession.id = :sessionId) " +
            "AND (:professorApproved IS NULL OR req.professorApproved = :professorApproved) " +
            "AND (:approved IS NULL OR req.isApproved = :approved) " +
            "AND (:processed IS NULL OR req.isProcessed = :processed) " +
            "AND (:student IS NULL OR req.student.index LIKE :student) " +
            "AND (:professor IS NULL OR req.professor.id = :professor)")
    Page<LateCourseEnrollmentStudentRequest> findAllFiltered(
            @Param("sessionId") Long sessionId,
            @Param("professorApproved") Boolean professorApproved,
            @Param("approved") Boolean approved,
            @Param("processed") Boolean processed,
            @Param("student") String student,
            @Param("professor") String professor,
            Pageable pageable
    );
}
