package Licences.service;

import Licences.model.Company;
import Licences.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    public final CompanyRepository companyRepository;

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company saveCompany(Company company){
        return companyRepository.save(company);
    }

    public void deleteById(long id){
        companyRepository.deleteById(id);
    }
}
