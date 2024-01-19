package mk.ukim.finki.molbi.repository;

import mk.ukim.finki.molbi.model.requests.GeneralStudentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GeneralStudentRequestRepository extends JpaRepository<GeneralStudentRequest, Long> {
    Long countByRequestSession_Id(Long sessionId);

    Page<GeneralStudentRequest> findByRequestSession_Id(Long requestSessionId, Pageable page);

    Page<GeneralStudentRequest> findByRequestSession_IdAndIsProcessed(Long requestSessionId, Boolean isProcessedFilter, PageRequest page);

    Page<GeneralStudentRequest> findByRequestSession_IdAndIsApprovedAndIsProcessed(Long requestSessionId, Boolean isApprovedFilter, Boolean isProcessedFilter, PageRequest page);

    Page<GeneralStudentRequest> findByRequestSession_IdAndStudent_IndexContaining(Long requestSessionId, String studentFilter, PageRequest page);

    Page<GeneralStudentRequest> findByRequestSession_IdAndIsApproved(Long requestSessionId, Boolean isApprovedFilter, PageRequest page);

    Page<GeneralStudentRequest> findByRequestSession_IdAndStudent_IndexContainingAndIsApprovedAndIsProcessed(Long requestSessionId, String studentFilter, Boolean isApprovedFilter, Boolean isProcessedFilter, PageRequest page);

    Page<GeneralStudentRequest> findByRequestSession_IdAndStudent_IndexContainingAndIsApproved(Long requestSessionId, String studentFilter, Boolean isApprovedFilter, PageRequest page);

    Page<GeneralStudentRequest> findByRequestSession_IdAndStudent_IndexContainingAndIsProcessed(Long requestSessionId, String studentFilter, Boolean isProcessedFilter, PageRequest page);
}