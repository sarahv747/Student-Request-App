package mk.ukim.finki.molbi.model.requests;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mk.ukim.finki.molbi.model.base.Course;
import mk.ukim.finki.molbi.model.base.Professor;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class LateCourseEnrollmentStudentRequest extends StudentRequest {

    @ManyToOne
    private Course course;

    @ManyToOne
    private Professor professor;

    private Boolean professorApproved;

    @Override
    public boolean canBeApproved() {
        return professorApproved;
    }

    public boolean canBeApprovedByProfessor() {
        return professorApproved == null || !professorApproved;
    }


}
