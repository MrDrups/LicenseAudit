package Licences.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Entity(name = "U01_USER")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long U01_ID;

    @Column(columnDefinition = "TEXT")
    private String U01_NAME;

    @Column(name = "U01_LOGIN", columnDefinition = "TEXT", unique = true)
    private String U01_LOGIN;

    @Column(columnDefinition = "TEXT")
    private String U01_PASS;

    @Column(columnDefinition = "TEXT", unique = true)
    private String U01_EMAIL;

    @ManyToOne
    @JoinColumn(name = "R01_ID", referencedColumnName = "R01_ID")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<LicenseLog> licenseLogs;
}
