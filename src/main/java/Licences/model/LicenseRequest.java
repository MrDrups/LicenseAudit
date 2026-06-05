package Licences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.Objects;

@Entity(name = "LR01_LICENSE_REQUEST")
@Getter
@Setter
public class LicenseRequest {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LR01_ID")
    private Long ID;

    @Column(name = "LR01_KEY", columnDefinition = "TEXT")
    private String KEY;

    @Column(name = "LR01_COMMENT", columnDefinition = "TEXT")
    private String COMMENT;

    @Column(name = "LR01_NOTIFICATION_PERIOD", columnDefinition = "TEXT")
    private String NOTIFICATION_PERIOD;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "LR01_START_DATE")
    private LocalDate START_DATE;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "LR01_END_DATE")
    private LocalDate END_DATE;

    @Column(name = "LR01_STATUS", columnDefinition = "TEXT")
    private String status = STATUS_PENDING;

    @Column(name = "LR01_CREATED_AT")
    private Timestamp createdAt;

    @Column(name = "LR01_REVIEWED_AT")
    private Timestamp reviewedAt;

    @ManyToOne
    @JoinColumn(name = "C01_ID", referencedColumnName = "C01_ID")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "LP01_ID", referencedColumnName = "LP01_ID")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private LicensePlan licensePlan;

    @ManyToOne
    @JoinColumn(name = "U01_AUTHOR_ID", referencedColumnName = "U01_ID")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User author;

    @ManyToOne
    @JoinColumn(name = "U01_REVIEWER_ID", referencedColumnName = "U01_ID")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User reviewer;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = new Timestamp(System.currentTimeMillis());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicenseRequest other)) return false;
        return ID != null && ID.equals(other.ID);
    }

    @Override
    public int hashCode() {
        return ID != null ? Objects.hash(ID) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "LicenseRequest{ID=" + ID + ", KEY='" + KEY + "', status=" + status + "}";
    }
}
