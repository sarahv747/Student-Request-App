package mk.ukim.finki.molbi.model.exceptions;

import mk.ukim.finki.molbi.model.requests.LateCourseEnrollmentStudentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class RequestNotApprovedByProfessorOrAlreadyApprovedException extends RuntimeException {
    public RequestNotApprovedByProfessorOrAlreadyApprovedException(LateCourseEnrollmentStudentRequest request) {
        super(!request.getProfessorApproved() ?
                "Request with id " + request.getId() + " is not approved by professor " + request.getProfessor().getName() :
                "Request with id " + request.getId() + " is already approved");
    }
}
