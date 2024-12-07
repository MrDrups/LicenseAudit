package Licences.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity(name = "C01_COMPANY")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C01_ID")
    private Long ID;

    @Column(name = "C01_NAME", columnDefinition = "TEXT")
    private String NAME;

    @Column(name = "C01_ADDRESS", columnDefinition = "TEXT")
    private String ADDRESS;

    @Column(name = "C01_CONTACT",columnDefinition = "TEXT")
    private String CONTACT;

    @Column(name = "C01_DESC", columnDefinition = "TEXT")
    private String DESC;

    @OneToMany(mappedBy = "company")
    private Set<License> licenses;
}