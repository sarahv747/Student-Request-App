package mk.ukim.finki.molbi.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LateCourseEnrollmentStudentRequestDto {

    private String course;

    private String professor;

    private String description;

    private Long requestSession;

    private String student;
}
