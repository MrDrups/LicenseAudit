package Licences.mapper;


import Licences.DTO.CompanyDTO;
import Licences.model.Company;

public class CompanyMapper {

    public static CompanyDTO toDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getID());
        dto.setName(company.getNAME());
        dto.setAddress(company.getADDRESS());
        dto.setContact(company.getCONTACT());
        dto.setDescription(company.getDESC());
        return dto;
    }

    public static Company toEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setID(dto.getId());
        company.setNAME(dto.getName());
        company.setADDRESS(dto.getAddress());
        company.setCONTACT(dto.getContact());
        company.setDESC(dto.getDescription());
        return company;
    }
}