package Licences.repository;

import Licences.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p FROM P01_PERMISSION p WHERE p.NAME = :name")
    Optional<Permission> findByName(@Param("name") String name);
}
