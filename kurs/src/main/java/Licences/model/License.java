package Licences.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Entity(name = "L01_LICENSE")
@Data
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long L01_ID;

    @Column(columnDefinition = "TEXT")
    private String L01_KEY;

    private Date L01_START_DATE;
    private Date L01_END_DATE;
    private boolean L01_REVOKED;
    private boolean L01_EXTENDED;

    @OneToMany(mappedBy = "license")
    private Set<LicenseLog> licenseLogs;

    @ManyToOne
    @JoinColumn(name = "C01_ID", referencedColumnName = "C01_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "LP01_ID", referencedColumnName = "LP01_ID")
    private LicensePlan licensePlan;
}

