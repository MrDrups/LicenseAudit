package Licences.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity(name = "LL01_LICENSE_LOG")
@Data
public class LicenseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long LL01_ID;

    @Column(columnDefinition = "TEXT")
    private String LL01_CHANGE_TYPE;

    private Timestamp LL01_CHANGE_DATE;

    @Column(columnDefinition = "TEXT")
    private String LL01_OLD_VALUE;

    @Column(columnDefinition = "TEXT")
    private String LL01_NEW_VALUE;

    @ManyToOne
    @JoinColumn(name = "U01_ID", referencedColumnName = "U01_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "L01_ID", referencedColumnName = "L01_ID")
    private License license;
}
