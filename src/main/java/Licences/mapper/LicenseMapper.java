package Licences.mapper;


import Licences.DTO.LicenseDTO;
import Licences.model.License;

public class LicenseMapper {

    public static LicenseDTO toDTO(License license) {
        LicenseDTO dto = new LicenseDTO();
        dto.setId(license.getID());
        dto.setKey(license.getKEY());
        dto.setStartDate(license.getSTART_DATE());
        dto.setEndDate(license.getEND_DATE());
        dto.setRevoked(license.isREVOKED());
        dto.setExtended(license.isEXTENDED());
        dto.setCompanyId(license.getCompany() != null ? license.getCompany().getID() : null);
        dto.setLicensePlanId(license.getLicensePlan() != null ? license.getLicensePlan().getID() : null);
        return dto;
    }

    public static License toEntity(LicenseDTO dto) {
        License license = new License();
        license.setID(dto.getId());
        license.setKEY(dto.getKey());
        license.setSTART_DATE(dto.getStartDate());
        license.setEND_DATE(dto.getEndDate());
        license.setREVOKED(dto.isRevoked());
        license.setEXTENDED(dto.isExtended());
        return license;
    }
}