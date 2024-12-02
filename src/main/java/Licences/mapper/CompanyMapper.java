package Licences.mapper;


import Licences.DTO.CompanyDTO;
import Licences.model.Company;

public class CompanyMapper {

    public static CompanyDTO toDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getC01_ID());
        dto.setName(company.getC01_NAME());
        dto.setAddress(company.getC01_ADRESS());
        dto.setContact(company.getC01_CONTACT());
        dto.setDescription(company.getC01_DESC());
        return dto;
    }

    public static Company toEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setC01_ID(dto.getId());
        company.setC01_NAME(dto.getName());
        company.setC01_ADRESS(dto.getAddress());
        company.setC01_CONTACT(dto.getContact());
        company.setC01_DESC(dto.getDescription());
        return company;
    }
}
