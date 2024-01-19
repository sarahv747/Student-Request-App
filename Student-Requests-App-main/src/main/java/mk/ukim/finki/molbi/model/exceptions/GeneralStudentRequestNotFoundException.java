package mk.ukim.finki.molbi.model.exceptions;

public class GeneralStudentRequestNotFoundException extends RuntimeException {
    public GeneralStudentRequestNotFoundException(Long id) {
        super("General student request not found with id " + id);
    }
}
