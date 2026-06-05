package Licences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Set;


@Entity(name = "U01_USER")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return ID != 0 && ID == other.ID;
    }

    @Override
    public int hashCode() {
        return ID != 0 ? Objects.hash(ID) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "User{ID=" + ID + ", LOGIN='" + LOGIN + "'}";
    }
}
