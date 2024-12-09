package Licences.repository;


import Licences.model.LicenseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseLogRepository extends JpaRepository<LicenseLog, Long> {
    @Query(value = "SELECT l.* FROM LL01_LICENSE_LOG l " +
            "LEFT JOIN u01_user u ON u.U01_ID = l.U01_ID " +
            "LEFT JOIN l01_license lic ON lic.L01_ID = l.L01_ID " +
            "WHERE LOWER(CONCAT(COALESCE(l.LL01_CHANGE_TYPE, ''), ' ', COALESCE(l.LL01_OLD_VALUE, ''), ' ', " +
            "COALESCE(l.LL01_NEW_VALUE, ''), ' ', COALESCE(CAST(l.LL01_CHANGE_DATE AS text), ''), ' ', " +
            "COALESCE(u.u01_login, ''), ' ', COALESCE(CAST(lic.L01_ID AS text), ''))) " +
            "LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<LicenseLog> search(@Param("keyword") String keyword);
}