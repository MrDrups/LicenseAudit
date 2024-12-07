package Licences.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity(name = "LP01_LICENSE_PLAN")
public class LicensePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LP01_ID;

    @Column(columnDefinition = "TEXT")
    private String LP01_NAME;

    private Long LP01_MAX_USERS;

    private BigDecimal LP01_PRICE;

    @OneToMany(mappedBy = "licensePlan")
    private Set<License> licenses;
}