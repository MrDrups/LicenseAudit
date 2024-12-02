package Licences.repository;

import Licences.model.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository  extends JpaRepository<Company,Long> {
    @Query("SELECT c FROM C01_COMPANY c WHERE CONCAT(c.C01_ID, ' ', c.C01_NAME, ' ', c.C01_DESC, ' ', c.C01_ADRESS, ' ', c.C01_CONTACT) LIKE %?1%")

    List<Company> search(String keyword);
}
