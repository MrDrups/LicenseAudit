package Licences.service;

import Licences.model.Company;
import Licences.model.License;
import Licences.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies(String keyword) {
        System.out.println("Вызов getAllLicenses с параметром: " + keyword);
        if (keyword == null || keyword.isEmpty()) {
            return companyRepository.findAll();
        }
        return companyRepository.search(keyword);
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public void saveCompany(Company company) {
        // Если компания имеет ID (т.е. это редактирование), то обновляем её
        if (company.getC01_ID() != null && company.getC01_ID() > 0) {
            companyRepository.save(company); // Обновляем существующую компанию
        } else {
            companyRepository.save(company); // Создаем новую компанию
        }
    }

    @Transactional
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

}
