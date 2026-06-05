package Licences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicensePlan other)) return false;
        return ID != null && ID.equals(other.ID);
    }

    @Override
    public int hashCode() {
        return ID != null ? Objects.hash(ID) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "LicensePlan{ID=" + ID + ", NAME='" + NAME + "'}";
    }
}
