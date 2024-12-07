package Licences.repository;


import Licences.model.LicensePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicensePlanRepository extends JpaRepository<LicensePlan,Long> {
    @Query("SELECT l FROM LP01_LICENSE_PLAN l WHERE CONCAT(l.ID, ' ', l.NAME, ' ', l.MAX_USERS, ' ', l.PRICE) LIKE %?1%")
    List<LicensePlan> search(String keyword);
}