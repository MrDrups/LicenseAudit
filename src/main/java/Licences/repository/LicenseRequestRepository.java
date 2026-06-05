package Licences.repository;

import Licences.model.LicenseRequest;
import Licences.model.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LicenseRequestRepository extends JpaRepository<LicenseRequest, Long> {

    List<LicenseRequest> findByStatusOrderByCreatedAtDesc(String status);

    List<LicenseRequest> findByAuthorOrderByCreatedAtDesc(User author);

    List<LicenseRequest> findByStatusNotOrderByCreatedAtDesc(String status);

    @Query("SELECT lr FROM LR01_LICENSE_REQUEST lr WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR LOWER(lr.KEY) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(lr.company.NAME) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(lr.licensePlan.NAME) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY lr.createdAt DESC")
    List<LicenseRequest> search(@Param("keyword") String keyword);
}
