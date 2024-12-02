package Licences.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity(name = "C01_COMPANY")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long C01_ID;

    @Column(columnDefinition = "TEXT")
    private String C01_NAME;

    @Column(columnDefinition = "TEXT")
    private String C01_ADRESS;

    @Column(columnDefinition = "TEXT")
    private String C01_CONTACT;

    @Column(columnDefinition = "TEXT")
    private String C01_DESC;

    @OneToMany(mappedBy = "company")
    private Set<License> licenses;

}
