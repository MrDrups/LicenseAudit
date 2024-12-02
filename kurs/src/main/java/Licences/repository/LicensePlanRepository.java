package Licences.repository;

import Licences.model.LicensePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicensePlanRepository extends JpaRepository<LicensePlan,Long> {
}
