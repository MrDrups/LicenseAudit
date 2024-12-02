package Licences.repository;

import Licences.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    @Query("SELECT l FROM L01_LICENSE l WHERE concat(l.L01_KEY, ' ', l.company.C01_NAME, ' ', l.licensePlan.LP01_NAME, ' ', l.L01_START_DATE, ' ', l.L01_END_DATE, ' ', l.L01_REVOKED, ' ', l.L01_EXTENDED) LIKE %?1%")
    List<License> search(String keyword);

    @Query("SELECT c.C01_NAME, COUNT(l) " +
            "FROM L01_LICENSE l " +
            "JOIN l.company c " +
            "GROUP BY c.C01_NAME")
    List<Object[]> countByCompany();

    // Лицензии по лицензионным планам
    @Query("SELECT lp.LP01_NAME, COUNT(l) FROM L01_LICENSE l JOIN l.licensePlan lp GROUP BY lp.LP01_NAME")
    List<Object[]> countLicensesByLicensePlan();
}
