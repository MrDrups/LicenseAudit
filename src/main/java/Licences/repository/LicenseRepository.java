package Licences.repository;

import Licences.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    @Query("SELECT l FROM L01_LICENSE l WHERE concat(l.KEY, ' ', l.company.NAME, ' ', l.licensePlan.NAME, ' ', l.START_DATE, ' ', l.END_DATE, ' ', l.REVOKED, ' ', l.EXTENDED) LIKE %?1%")
    List<License> search(String keyword);

    @Query("SELECT c.NAME, COUNT(l) " + "FROM L01_LICENSE l " + "JOIN l.company c " + "GROUP BY c.NAME")
    List<Object[]> countByCompany();

    // Лицензии по лицензионным планам
    @Query("SELECT lp.NAME, COUNT(l) FROM L01_LICENSE l JOIN l.licensePlan lp GROUP BY lp.NAME")
    List<Object[]> countLicensesByLicensePlan();
}