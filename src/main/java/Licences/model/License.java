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
    private Long L01_ID;

    @Column(columnDefinition = "TEXT")
    private String L01_KEY;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate L01_START_DATE;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate L01_END_DATE;
    private boolean L01_REVOKED;
    private boolean L01_EXTENDED;

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
                "ID=" + L01_ID +
                ", Key='" + L01_KEY + '\'' +
                ", Company=" + (company != null ? company.getC01_NAME() : "null") +
                ", LicensePlan=" + (licensePlan != null ? licensePlan.getLP01_NAME() : "null") +
                ", StartDate=" + L01_START_DATE +
                ", EndDate=" + L01_END_DATE +
                ", Revoked=" + L01_REVOKED +
                ", Extended=" + L01_EXTENDED +
                '}';
    }
}

