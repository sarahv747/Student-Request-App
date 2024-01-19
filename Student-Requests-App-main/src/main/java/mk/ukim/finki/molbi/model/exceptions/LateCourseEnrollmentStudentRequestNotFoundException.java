package mk.ukim.finki.molbi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LateCourseEnrollmentStudentRequestNotFoundException extends RuntimeException {
    public LateCourseEnrollmentStudentRequestNotFoundException(Long id) {
        super("Late Course Enrollment Student request not found with id " + id);
    }
}
