package Licences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity(name = "P01_PERMISSION")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P01_ID")
    private Long ID;

    @Column(name = "P01_NAME", columnDefinition = "TEXT", unique = true)
    private String NAME;

    @Column(name = "P01_DESCRIPTION", columnDefinition = "TEXT")
    private String DESCRIPTION;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission other)) return false;
        return ID != null && ID.equals(other.ID);
    }

    @Override
    public int hashCode() {
        return ID != null ? Objects.hash(ID) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "Permission{ID=" + ID + ", NAME='" + NAME + "'}";
    }
}
