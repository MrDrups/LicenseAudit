package Licences.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.Objects;

@Entity(name = "LL01_LICENSE_LOG")
@Getter
@Setter
public class LicenseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LL01_ID")
    private Long ID;

    @Column(name = "LL01_CHANGE_TYPE", columnDefinition = "TEXT")
    private String CHANGE_TYPE;

    @Column(name = "LL01_CHANGE_DATE")
    private Timestamp CHANGE_DATE;

    @Column(name = "LL01_OLD_VALUE", columnDefinition = "TEXT")
    private String OLD_VALUE;

    @Column(name="LL01_NEW_VALUE", columnDefinition = "TEXT")
    private String NEW_VALUE;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "U01_ID", referencedColumnName = "U01_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "L01_ID", referencedColumnName = "L01_ID")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private License license;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicenseLog other)) return false;
        return ID != null && ID.equals(other.ID);
    }

    @Override
    public int hashCode() {
        return ID != null ? Objects.hash(ID) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "LicenseLog{ID=" + ID + ", type='" + CHANGE_TYPE + "'}";
    }
}
