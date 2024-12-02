package Licences.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Entity(name="R01_ROLE")
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long R01_ID;

    @Column(columnDefinition = "TEXT", unique = true)
    private String R01_NAME;

    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
