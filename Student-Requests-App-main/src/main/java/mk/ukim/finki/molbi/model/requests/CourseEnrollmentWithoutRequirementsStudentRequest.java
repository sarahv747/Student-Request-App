package mk.ukim.finki.molbi.model.requests;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import mk.ukim.finki.molbi.model.base.Course;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class CourseEnrollmentWithoutRequirementsStudentRequest extends StudentRequest {

    @ManyToOne
    private Course course;

}

