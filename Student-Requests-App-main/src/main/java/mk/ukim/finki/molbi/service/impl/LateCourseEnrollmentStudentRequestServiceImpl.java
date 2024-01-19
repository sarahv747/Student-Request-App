package mk.ukim.finki.molbi.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.base.Course;
import mk.ukim.finki.molbi.model.base.Professor;
import mk.ukim.finki.molbi.model.base.Student;
import mk.ukim.finki.molbi.model.dtos.FilterDto;
import mk.ukim.finki.molbi.model.dtos.LateCourseEnrollmentStudentRequestDto;
import mk.ukim.finki.molbi.model.exceptions.*;
import mk.ukim.finki.molbi.model.requests.LateCourseEnrollmentStudentRequest;
import mk.ukim.finki.molbi.model.requests.RequestSession;
import mk.ukim.finki.molbi.repository.*;
import mk.ukim.finki.molbi.service.LateCourseEnrollmentStudentRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class LateCourseEnrollmentStudentRequestServiceImpl implements LateCourseEnrollmentStudentRequestService {

    private final LateCourseEnrollmentStudentRequestRepository requestRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final RequestSessionRepository sessionRepository;

    @Transactional
    @Override
    public Page<LateCourseEnrollmentStudentRequest> findByRequestSessionWithPagination(Long sessionId,
                                                                                       int pageNum,
                                                                                       int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.ASC, "id");
        return requestRepository.findByRequestSession_Id(sessionId, pageRequest);
    }

    @Transactional
    @Override
    public Page<LateCourseEnrollmentStudentRequest> findByRequestSessionFilteredWithPagination(Long sessionId,
                                                                                               int pageNum,
                                                                                               int pageSize,
                                                                                               FilterDto dto) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        return requestRepository.findAllFiltered(sessionId, dto.getProfessorApproved(), dto.getApproved(),
                dto.getProcessed(), !dto.getStudent().isEmpty() ? dto.getStudent() : null,
                !dto.getProfessor().isEmpty() ? dto.getProfessor() : null, pageRequest);

    }

    @Override
    public LateCourseEnrollmentStudentRequest findById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new LateCourseEnrollmentStudentRequestNotFoundException(id));
    }

    @Override
    public void create(LateCourseEnrollmentStudentRequestDto dto) {
        if (dto.getStudent().endsWith(".") || dto.getCourse().endsWith(".") || dto.getProfessor().endsWith(".")
                || dto.getDescription().isBlank()) {
            throw new AllFieldsNotFilledException();
        }

        LateCourseEnrollmentStudentRequest request = new LateCourseEnrollmentStudentRequest();

        Student student = studentRepository.findById(dto.getStudent())
                .orElseThrow(() -> new StudentNotFoundException(dto.getStudent()));

        String description = dto.getDescription();

        RequestSession session = sessionRepository.findById(dto.getRequestSession())
                .orElseThrow(() -> new RequestSessionNotFoundException(dto.getRequestSession()));

        Course course = courseRepository.findById(dto.getCourse())
                .orElseThrow(() -> new CourseNotFoundException(dto.getCourse()));
        Professor professor = professorRepository.findById(dto.getProfessor())
                .orElseThrow(() -> new ProfessorNotFoundException(dto.getProfessor()));

        request.setStudent(student);
        request.setDescription(description);
        request.setRequestSession(session);
        request.setDateCreated(LocalDate.now());
        request.setCourse(course);
        request.setProfessor(professor);
        request.setProfessorApproved(false);
        request.setIsProcessed(false);
        requestRepository.save(request);
    }

    @Override
    public void edit(Long id, LateCourseEnrollmentStudentRequestDto dto) {
        if (dto.getStudent().endsWith(".") || dto.getCourse().endsWith(".") || dto.getProfessor().endsWith(".")
                || dto.getDescription().isBlank()) {
            throw new AllFieldsNotFilledException();
        }
        LateCourseEnrollmentStudentRequest request = findById(id);

        Student student = studentRepository.findById(dto.getStudent())
                .orElseThrow(() -> new StudentNotFoundException(dto.getStudent()));

        Course course = courseRepository.findById(dto.getCourse())
                .orElseThrow(() -> new CourseNotFoundException(dto.getCourse()));

        Professor professor = professorRepository.findById(dto.getProfessor())
                .orElseThrow(() -> new ProfessorNotFoundException(dto.getProfessor()));

        request.setStudent(student);
        request.setCourse(course);
        request.setProfessor(professor);
        request.setDescription(dto.getDescription());
        requestRepository.save(request);
    }

    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public void processApprovalByProfessor(Long id) {
        LateCourseEnrollmentStudentRequest request = findById(id);
        if (request.canBeApprovedByProfessor()) {
            request.setProfessorApproved(true);
            requestRepository.save(request);
        }
    }

    @Override
    public void updateStatus(Long id, Boolean action, String rejectReason) {
        LateCourseEnrollmentStudentRequest request = findById(id);
        if (action && (!request.canBeApproved() || (request.getIsApproved() != null && request.getIsApproved()))) {
            throw new RequestNotApprovedByProfessorOrAlreadyApprovedException(request);
        }

        if (!action && !request.canBeRejected()) {
            throw new RequestAlreadyProcessedOrAlreadyRejectedException(request);
        }

        request.setIsApproved(action);
        request.setRejectionReason(rejectReason);
        requestRepository.save(request);
    }

    @Override
    public void markAsProcessed(Long id) {
        LateCourseEnrollmentStudentRequest request = findById(id);
        if (request.getIsApproved() == null || !request.canBeMarkedAsProcessed()) {
            throw new RequestNotApprovedOrAlreadyProcessedException(request);
        }
        request.setIsProcessed(true);
        request.setDateProcessed(LocalDate.now());
        requestRepository.save(request);
    }

    @Override
    public Long totalRequestBySession(Long sessionId) {
        return requestRepository.countByRequestSession_Id(sessionId);
    }

}





