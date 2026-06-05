package Licences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity(name = "L01_LICENSE")
@Getter
@Setter
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "L01_ID")
    private Long ID;

    @Column(name="L01_KEY", columnDefinition = "TEXT")
    private String KEY;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "L01_START_DATE")
    private LocalDate START_DATE;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "L01_END_DATE")
    private LocalDate END_DATE;

    @Column(name = "L01_REVOKED")
    private boolean REVOKED;

    @Column(name = "L01_EXTENDED")
    private boolean EXTENDED;

    @Column(name = "L01_NOTIFICATION_PERIOD", columnDefinition = "TEXT")
    private String NOTIFICATION_PERIOD;

    @Column(name = "L01_COMMENT", columnDefinition = "TEXT")
    private String COMMENT;

    @OneToMany(mappedBy = "license")
    private Set<LicenseLog> licenseLogs;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "C01_ID", referencedColumnName = "C01_ID")
    private Company company;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "LP01_ID", referencedColumnName = "LP01_ID")
    private LicensePlan licensePlan;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof License other)) return false;
        return ID != null && ID.equals(other.ID);
    }

    @Override
    public int hashCode() {
        return ID != null ? Objects.hash(ID) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "{" +
                "ID=" + ID +
                ", Ключ='" + KEY + '\'' +
                ", Компания=" + (company != null ? company.getNAME() : "null") +
                ", План=" + (licensePlan != null ? licensePlan.getNAME() : "null") +
                ", Начало=" + START_DATE +
                ", Конец=" + END_DATE +
                ", Отозвана=" + REVOKED +
                ", Продлена=" + EXTENDED +
                "}";
    }
}
