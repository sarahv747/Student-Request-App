package mk.ukim.finki.molbi.model.requests;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.molbi.model.base.Semester;
import mk.ukim.finki.molbi.model.enums.RequestType;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RequestSession {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime timeFrom;

    private LocalDateTime timeTo;

    @ManyToOne
    private Semester semester;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Column(length = 5000)
    private String description;

    @Column(length = 5000)
    private String approvalNote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RequestSession requestSession = (RequestSession) o;
        return getId() != null && Objects.equals(getId(), requestSession.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
