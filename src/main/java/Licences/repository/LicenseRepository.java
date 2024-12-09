package Licences.repository;

import Licences.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    @Query(value = "SELECT l.* FROM L01_LICENSE l " +
            "LEFT JOIN c01_company c ON c.c01_id = l.c01_id " +
            "LEFT JOIN lp01_license_plan lp ON lp.lp01_id = l.lp01_id " +
            "WHERE LOWER(CONCAT(COALESCE(l.l01_id::text, ''), ' ', COALESCE(l.l01_key, ''), ' ', " +
            "COALESCE(c.c01_name, ''), ' ', COALESCE(lp.lp01_name, ''), ' ', " +
            "COALESCE(l.l01_start_date::text, ''), ' ', COALESCE(l.l01_end_date::text, ''), ' ', " +
            "COALESCE(l.l01_revoked::text, ''), ' ', COALESCE(l.l01_extended::text, ''))) " +
            "LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<License> search(@Param("keyword") String keyword);


    @Query("SELECT c.NAME, COUNT(l) " + "FROM L01_LICENSE l " + "JOIN l.company c " + "GROUP BY c.NAME")
    List<Object[]> countByCompany();

    @Query("SELECT lp.NAME, COUNT(l) FROM L01_LICENSE l JOIN l.licensePlan lp GROUP BY lp.NAME")
    List<Object[]> countLicensesByLicensePlan();
}