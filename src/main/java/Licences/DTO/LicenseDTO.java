package Licences.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LicenseDTO {
    private Long id;
    private String key;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean revoked;
    private boolean extended;
    private Long companyId;      // ID компании
    private Long licensePlanId;  // ID лицензионного плана
}
