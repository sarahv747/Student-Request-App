package mk.ukim.finki.molbi.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterDto {

    private Boolean professorApproved;

    private Boolean approved;

    private Boolean processed;

    private String professor;

    private String student;
}
