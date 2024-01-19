package mk.ukim.finki.molbi.model.base;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudyProgram {

    @Id
    private String code;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudyProgram that = (StudyProgram) o;
        return getCode() != null && Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}



