package mk.ukim.finki.molbi.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.base.Student;
import mk.ukim.finki.molbi.model.dtos.GeneralStudentRequestDto;
import mk.ukim.finki.molbi.model.exceptions.GeneralStudentRequestNotFoundException;
import mk.ukim.finki.molbi.model.exceptions.RequestSessionNotFoundException;
import mk.ukim.finki.molbi.model.requests.GeneralStudentRequest;
import mk.ukim.finki.molbi.repository.GeneralStudentRequestRepository;
import mk.ukim.finki.molbi.repository.RequestSessionRepository;
import mk.ukim.finki.molbi.service.GeneralStudentRequestService;
import mk.ukim.finki.molbi.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class GeneralStudentRequestServiceImpl implements GeneralStudentRequestService {
    private final GeneralStudentRequestRepository generalStudentRequestRepository;
    private final StudentService studentService;
    private final RequestSessionRepository requestSessionRepository;

    @Transactional
    @Override
    public Page<GeneralStudentRequest> showPage(Long requestSessionId, int pageNum, int pageSize,
                                                               String studentFilter, Boolean isApprovedFilter, Boolean isProcessedFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest page = PageRequest.of(pageNum - 1, pageSize, sort);

        if (studentFilter != null && !studentFilter.isEmpty() && isApprovedFilter != null && isProcessedFilter != null) {
            System.out.println("Using studentFilter, isApprovedFilter, and isProcessedFilter");
            return generalStudentRequestRepository.findByRequestSession_IdAndStudent_IndexContainingAndIsApprovedAndIsProcessed(
                    requestSessionId, studentFilter, isApprovedFilter, isProcessedFilter, page);
        }

        if (studentFilter != null && !studentFilter.isEmpty() && isApprovedFilter != null) {
            System.out.println("Using studentFilter and isApprovedFilter");
            return generalStudentRequestRepository.findByRequestSession_IdAndStudent_IndexContainingAndIsApproved(
                    requestSessionId, studentFilter, isApprovedFilter, page);
        }

        if (studentFilter != null && !studentFilter.isEmpty() && isProcessedFilter != null) {
            System.out.println("Using studentFilter and isProcessedFilter");
            return generalStudentRequestRepository.findByRequestSession_IdAndStudent_IndexContainingAndIsProcessed(
                    requestSessionId, studentFilter, isProcessedFilter, page);
        }

        if (studentFilter != null && !studentFilter.isEmpty()) {
            System.out.println("Using studentFilter");
            return generalStudentRequestRepository.findByRequestSession_IdAndStudent_IndexContaining(
                    requestSessionId, studentFilter, page);
        }
        if (isApprovedFilter != null && isProcessedFilter != null) {
            System.out.println("Using isApprovedFilter and isProcessedFilter");
            return generalStudentRequestRepository.findByRequestSession_IdAndIsApprovedAndIsProcessed(
                    requestSessionId, isApprovedFilter, isProcessedFilter, page);
        }

        if (isApprovedFilter != null) {
            System.out.println("Using isApprovedFilter");
            return generalStudentRequestRepository.findByRequestSession_IdAndIsApproved(
                    requestSessionId, isApprovedFilter, page);
        }

        if (isProcessedFilter != null) {
            System.out.println("Using isProcessedFilter");
            return generalStudentRequestRepository.findByRequestSession_IdAndIsProcessed(
                    requestSessionId, isProcessedFilter, page);
        }

        System.out.println("Using default filter");
        return generalStudentRequestRepository.findByRequestSession_Id(requestSessionId, page);
    }

    @Override
    public GeneralStudentRequest findById(Long id) {
        return generalStudentRequestRepository.findById(id).orElseThrow(() -> new GeneralStudentRequestNotFoundException(id));
    }

    public void create(GeneralStudentRequestDto dto){
        GeneralStudentRequest request = new GeneralStudentRequest();
        request.setDescription(dto.getDescription());
        Student student = studentService.findByIndex(dto.getStudent());
        request.setStudent(student);
        request.setDateCreated(LocalDate.now());
        request.setRequestSession(requestSessionRepository
                .findById(dto.getRequestSession())
                .orElseThrow(() -> new RequestSessionNotFoundException(dto.getRequestSession()))
        );
        request.setIsProcessed(Boolean.FALSE);
        generalStudentRequestRepository.save(request);
    }

    @Override
    public void status(Long id, Boolean isApproved, String rejectionReason){
        GeneralStudentRequest request = findById(id);
        request.setIsApproved(isApproved);
        request.setRejectionReason(rejectionReason);
        generalStudentRequestRepository.save(request);
    }

    @Override
    public void markProcessed(Long id, Boolean isProcessed){
        GeneralStudentRequest request = findById(id);
        request.setIsProcessed(isProcessed);
        generalStudentRequestRepository.save(request);
    }

    @Override
    public void delete(Long id){
        generalStudentRequestRepository.deleteById(id);
    }
}
