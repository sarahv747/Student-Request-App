package mk.ukim.finki.molbi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SemesterNotFoundException extends RuntimeException {
    public SemesterNotFoundException(String code) {
        super("Semester not found with code " + code);
    }
}
