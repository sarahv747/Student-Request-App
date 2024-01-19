package mk.ukim.finki.molbi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestSessionNotFoundException extends RuntimeException {
    public RequestSessionNotFoundException(Long id) {
        super("Request session not found with id " + id);
    }
}

