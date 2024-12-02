package Licences.repository;

import Licences.model.LicenseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseLogRepository extends JpaRepository<LicenseLog, Long> {
}
