package mk.ukim.finki.molbi.service.impl;

import mk.ukim.finki.molbi.model.base.Semester;
import mk.ukim.finki.molbi.model.dtos.RequestSessionDto;
import mk.ukim.finki.molbi.model.enums.RequestType;
import mk.ukim.finki.molbi.model.exceptions.RequestSessionNotFoundException;
import mk.ukim.finki.molbi.model.requests.RequestSession;
import mk.ukim.finki.molbi.repository.RequestSessionRepository;
import mk.ukim.finki.molbi.service.RequestSessionService;
import mk.ukim.finki.molbi.service.SemesterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestSessionServiceImpl implements RequestSessionService {
    private final SemesterService semesterService;

    private final RequestSessionRepository repository;

    public RequestSessionServiceImpl(SemesterService semesterService, RequestSessionRepository repository) {
        this.semesterService = semesterService;
        this.repository = repository;
    }

    @Override
    public List<RequestSession> findAll() {
        return repository.findAll();
    }

    @Override
    public RequestSession findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RequestSessionNotFoundException(id));
    }


    @Override
    public void save(Long id, RequestSessionDto dto) {
        RequestSession requestSession;
        if (id != null) {
            requestSession = this.findById(id);
        } else {
            requestSession = new RequestSession();
        }

        requestSession.setDescription(dto.getDescription());
        requestSession.setRequestType(dto.getRequestType());

        Semester semester = semesterService.findById(dto.getSemester());
        requestSession.setSemester(semester);

        requestSession.setTimeFrom(dto.getTimeFrom());
        requestSession.setTimeTo(dto.getTimeTo());
        requestSession.setApprovalNote(dto.getApprovalNote());
        repository.save(requestSession);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean isSafeToDelete(Long id) {
        RequestSession requestSession = findById(id);
        RequestType requestType = requestSession.getRequestType();
        //the code from the other branches is needed to write this code
        //no services for all the requests
        return false;
    }

    @Override
    public Page<RequestSession> getAllEntities(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page - 1, size, Sort.Direction.DESC, "timeFrom"));
    }
}
