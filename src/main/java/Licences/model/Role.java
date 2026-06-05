package Licences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity(name="R01_ROLE")
@Getter
@Setter

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "R01_ID")
    private long ID;

    @Column(name = "R01_NAME",columnDefinition = "TEXT", unique = true)
    private String NAME;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "R01_P01_ROLE_PERMISSIONS",
            joinColumns = @JoinColumn(name = "R01_ID"),
            inverseJoinColumns = @JoinColumn(name = "P01_ID")
    )
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role other)) return false;
        return ID != 0 && ID == other.ID;
    }

    @Override
    public int hashCode() {
        return ID != 0 ? Objects.hash(ID) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "Role{ID=" + ID + ", NAME='" + NAME + "'}";
    }
}
