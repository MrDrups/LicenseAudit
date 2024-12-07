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
    @Column(name = "LP01_ID")
    private Long ID;

    @Column(name = "LP01_NAME", columnDefinition = "TEXT")
    private String NAME;

    @Column(name = "LP01_MAX_USERS")
    private Long MAX_USERS;

    @Column(name = "LP01_PRICE")
    private BigDecimal PRICE;

    @OneToMany(mappedBy = "licensePlan")
    private Set<License> licenses;
}