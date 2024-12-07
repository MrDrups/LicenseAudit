package Licences.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;


@Entity(name = "U01_USER")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "U01_ID")
    private long ID;

    @Column(name = "U01_NAME", columnDefinition = "TEXT")
    private String NAME;

    @Column(name = "U01_LOGIN", columnDefinition = "TEXT", unique = true)
    private String LOGIN;

    @Column(name = "U01_PASS", columnDefinition = "TEXT")
    private String PASS;

    @Column(name = "U01_EMAIL", columnDefinition = "TEXT", unique = true)
    private String EMAIL;

    @ManyToOne
    @JoinColumn(name = "R01_ID", referencedColumnName = "R01_ID")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<LicenseLog> licenseLogs;
}