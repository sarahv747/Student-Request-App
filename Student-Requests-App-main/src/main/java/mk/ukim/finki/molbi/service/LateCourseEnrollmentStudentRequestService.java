package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.dtos.FilterDto;
import mk.ukim.finki.molbi.model.dtos.LateCourseEnrollmentStudentRequestDto;
import mk.ukim.finki.molbi.model.requests.LateCourseEnrollmentStudentRequest;
import org.springframework.data.domain.Page;

public interface LateCourseEnrollmentStudentRequestService {

    Page<LateCourseEnrollmentStudentRequest> findByRequestSessionWithPagination(Long sessionId, int pageNum, int pageSize);

    Page<LateCourseEnrollmentStudentRequest> findByRequestSessionFilteredWithPagination(Long sessionId, int pageNum, int pageSize, FilterDto dto);

    LateCourseEnrollmentStudentRequest findById(Long id);

    void create(LateCourseEnrollmentStudentRequestDto dto);

    void edit(Long id, LateCourseEnrollmentStudentRequestDto dto);

    void delete(Long id);

    void processApprovalByProfessor(Long id);

    void updateStatus(Long id, Boolean action, String rejectReason);

    void markAsProcessed(Long id);

    Long totalRequestBySession(Long sessionId);


}
