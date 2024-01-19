package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.dtos.GeneralStudentRequestDto;
import mk.ukim.finki.molbi.model.requests.GeneralStudentRequest;
import org.springframework.data.domain.Page;


public interface GeneralStudentRequestService {
    Page<GeneralStudentRequest> showPage(Long requestSessionId, int pageNum, int pageSize,
                                                        String studentFilter, Boolean isApprovedFilter, Boolean isProcessedFilter);

    GeneralStudentRequest findById(Long id);

    void create(GeneralStudentRequestDto dto);

    void status(Long id, Boolean isApproved, String rejectionReason);

    void markProcessed(Long id, Boolean isProcessed);

    void delete(Long id);
}
