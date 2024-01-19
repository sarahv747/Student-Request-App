package mk.ukim.finki.molbi.model.base;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.molbi.model.enums.SemesterType;
import mk.ukim.finki.molbi.model.enums.StudyCycle;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Semester {

    // 2022/2023-W
    @Id
    private String code;

    // 2022/2023
    private String year;

    @Enumerated(EnumType.STRING)
    private SemesterType semesterType;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate enrollmentStartDate;

    private LocalDate enrollmentEndDate;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<StudyCycle> cycle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Semester semester = (Semester) o;
        return getCode() != null && Objects.equals(getCode(), semester.getCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
