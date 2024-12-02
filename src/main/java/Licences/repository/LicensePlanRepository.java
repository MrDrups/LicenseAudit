package Licences.repository;


import Licences.model.LicensePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicensePlanRepository extends JpaRepository<LicensePlan,Long> {
    @Query("SELECT l FROM LP01_LICENSE_PLAN l WHERE CONCAT(l.LP01_ID, ' ', l.LP01_NAME, ' ', l.LP01_MAX_USERS, ' ', l.LP01_PRICE) LIKE %?1%")
    List<LicensePlan> search(String keyword);
}
