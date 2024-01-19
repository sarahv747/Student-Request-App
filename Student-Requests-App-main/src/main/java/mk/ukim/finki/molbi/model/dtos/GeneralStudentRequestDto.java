package mk.ukim.finki.molbi.model.dtos;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.molbi.model.base.Student;

@Getter
@Setter
public class GeneralStudentRequestDto {
    private String description;
    private Long requestSession;
    private String student;
}
