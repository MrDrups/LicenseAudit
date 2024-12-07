package Licences.mapper;

import Licences.DTO.LicensePlanDTO;
import Licences.model.LicensePlan;


public class LicensePlanMapper {

    public static LicensePlanDTO toDTO(LicensePlan licensePlan) {
        LicensePlanDTO dto = new LicensePlanDTO();
        dto.setId(licensePlan.getID());
        dto.setName(licensePlan.getNAME());
        dto.setMaxUsers(licensePlan.getMAX_USERS());
        dto.setPrice(licensePlan.getPRICE());
        return dto;
    }

    public static LicensePlan toEntity(LicensePlanDTO dto) {
        LicensePlan licensePlan = new LicensePlan();
        licensePlan.setID(dto.getId());
        licensePlan.setNAME(dto.getName());
        licensePlan.setMAX_USERS(dto.getMaxUsers());
        licensePlan.setPRICE(dto.getPrice());
        return licensePlan;
    }
}