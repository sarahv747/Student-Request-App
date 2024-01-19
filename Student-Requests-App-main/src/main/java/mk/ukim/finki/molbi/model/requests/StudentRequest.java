package mk.ukim.finki.molbi.model.requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mk.ukim.finki.molbi.model.base.Student;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public abstract class StudentRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Student student;

    @Lob
    private String description;

    @ManyToOne
    private RequestSession requestSession;

    private Boolean isApproved;

    private LocalDate dateCreated;

    private LocalDate dateProcessed;

    @Column(length = 3_000)
    private String rejectionReason;

    /**
     * The approved requests need to be processed in order to be valid. This flag indicates the corresponding status
     */
    private Boolean isProcessed;

    public boolean canBeApproved() {
        return !isProcessed && !isApproved;
    }

    public boolean canBeRejected() {
        return !isProcessed && (isApproved == null || isApproved);
    }

    public boolean canBeMarkedAsProcessed() {
        return isApproved && !isProcessed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentRequest studentRequest = (StudentRequest) o;
        return getId() != null && Objects.equals(getId(), studentRequest.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
