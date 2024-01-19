package mk.ukim.finki.molbi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AllFieldsNotFilledException extends RuntimeException {
    public AllFieldsNotFilledException() {
        super("All fields need to be filled.");
    }
}
