package Licences.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LicensePlanDTO {
    private Long id;
    private String name;
    private Long maxUsers;
    private BigDecimal price;
}
