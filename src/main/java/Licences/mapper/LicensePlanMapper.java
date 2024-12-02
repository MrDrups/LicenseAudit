package Licences.mapper;

import Licences.DTO.LicensePlanDTO;
import Licences.model.LicensePlan;


public class LicensePlanMapper {

    public static LicensePlanDTO toDTO(LicensePlan licensePlan) {
        LicensePlanDTO dto = new LicensePlanDTO();
        dto.setId(licensePlan.getLP01_ID());
        dto.setName(licensePlan.getLP01_NAME());
        dto.setMaxUsers(licensePlan.getLP01_MAX_USERS());
        dto.setPrice(licensePlan.getLP01_PRICE());
        return dto;
    }

    public static LicensePlan toEntity(LicensePlanDTO dto) {
        LicensePlan licensePlan = new LicensePlan();
        licensePlan.setLP01_ID(dto.getId());
        licensePlan.setLP01_NAME(dto.getName());
        licensePlan.setLP01_MAX_USERS(dto.getMaxUsers());
        licensePlan.setLP01_PRICE(dto.getPrice());
        return licensePlan;
    }
}
