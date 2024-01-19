package mk.ukim.finki.molbi.model.requests;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mk.ukim.finki.molbi.model.base.StudyProgram;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class ChangeStudyProgramStudentRequest extends StudentRequest {

    @ManyToOne
    private StudyProgram newStudyProgram;

    @ManyToOne
    private StudyProgram oldStudyProgram;
}
