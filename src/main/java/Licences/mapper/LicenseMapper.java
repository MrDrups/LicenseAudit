package Licences.mapper;


import Licences.DTO.LicenseDTO;
import Licences.model.License;

public class LicenseMapper {

    public static LicenseDTO toDTO(License license) {
        LicenseDTO dto = new LicenseDTO();
        dto.setId(license.getL01_ID());
        dto.setKey(license.getL01_KEY());
        dto.setStartDate(license.getL01_START_DATE());
        dto.setEndDate(license.getL01_END_DATE());
        dto.setRevoked(license.isL01_REVOKED());
        dto.setExtended(license.isL01_EXTENDED());
        dto.setCompanyId(license.getCompany() != null ? license.getCompany().getC01_ID() : null);
        dto.setLicensePlanId(license.getLicensePlan() != null ? license.getLicensePlan().getLP01_ID() : null);
        return dto;
    }

    public static License toEntity(LicenseDTO dto) {
        License license = new License();
        license.setL01_ID(dto.getId());
        license.setL01_KEY(dto.getKey());
        license.setL01_START_DATE(dto.getStartDate());
        license.setL01_END_DATE(dto.getEndDate());
        license.setL01_REVOKED(dto.isRevoked());
        license.setL01_EXTENDED(dto.isExtended());
        return license;
    }
}