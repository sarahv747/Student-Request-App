package mk.ukim.finki.molbi.service;

import mk.ukim.finki.molbi.model.dtos.RequestSessionDto;
import mk.ukim.finki.molbi.model.requests.RequestSession;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RequestSessionService {

    List<RequestSession> findAll();

    RequestSession findById(Long id);

    void save(Long id, RequestSessionDto dto);

    void delete(Long id);

    boolean isSafeToDelete(Long id);

    Page<RequestSession> getAllEntities(Integer page, Integer size);
}
