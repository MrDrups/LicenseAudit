package Licences.repository;


import Licences.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("SELECT r FROM R01_ROLE r WHERE r.NAME = :name")
    Optional<Role> findByName(@Param("name") String name);

    @Query("SELECT r FROM R01_ROLE r WHERE concat(r.NAME, ' ',r.ID) LIKE %?1%")
    List<Role> search(String keyword);
}