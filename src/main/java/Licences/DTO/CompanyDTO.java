package Licences.DTO;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String address;
    private String contact;
    private String description;
}