package Licences.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "L01_LICENSE")
@Data
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
    public String toString() {
        return "License{" +
                "ID=" + ID +
                ", Key='" + KEY + '\'' +
                ", Company=" + (company != null ? company.getNAME() : "null") +
                ", LicensePlan=" + (licensePlan != null ? licensePlan.getNAME() : "null") +
                ", StartDate=" + START_DATE +
                ", EndDate=" + END_DATE +
                ", Revoked=" + REVOKED +
                ", Extended=" + EXTENDED +
                '}';
    }
}