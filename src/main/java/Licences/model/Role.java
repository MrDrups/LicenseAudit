package Licences.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Entity(name="R01_ROLE")
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "R01_ID")
    private long ID;

    @Column(name = "R01_NAME",columnDefinition = "TEXT", unique = true)
    private String NAME;

    @OneToMany(mappedBy = "role")
    private Set<User> users;
}