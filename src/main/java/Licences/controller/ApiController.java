package Licences.controller;

import Licences.DTO.CompanyDTO;
import Licences.DTO.LicenseDTO;
import Licences.DTO.LicensePlanDTO;
import Licences.mapper.CompanyMapper;
import Licences.mapper.LicenseMapper;
import Licences.mapper.LicensePlanMapper;
import Licences.model.Company;
import Licences.model.License;
import Licences.model.LicensePlan;
import Licences.repository.CompanyRepository;
import Licences.repository.LicensePlanRepository;
import Licences.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final LicenseRepository licenseRepository;
    private final CompanyRepository companyRepository;
    private final LicensePlanRepository licensePlanRepository;


    @GetMapping("/licenses")
    public ResponseEntity<List<LicenseDTO>> getAllLicenses() {
        List<License> licenses = licenseRepository.findAll();
        List<LicenseDTO> licenseDTOs = licenses.stream()
                .map(LicenseMapper::toDTO)
                .toList();
        return ResponseEntity.ok(licenseDTOs);
    }


    @GetMapping("/licenses/{id}")
    public ResponseEntity<LicenseDTO> getLicenseById(@PathVariable Long id) {
        return licenseRepository.findById(id)
                .map(LicenseMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/licenses")
    public ResponseEntity<LicenseDTO> createLicense(@RequestBody LicenseDTO licenseDTO) {
        License license = LicenseMapper.toEntity(licenseDTO);
        // Устанавливаем связанные объекты, если указаны их ID
        license.setCompany(licenseDTO.getCompanyId() != null
                ? companyRepository.findById(licenseDTO.getCompanyId()).orElse(null)
                : null);
        license.setLicensePlan(licenseDTO.getLicensePlanId() != null
                ? licensePlanRepository.findById(licenseDTO.getLicensePlanId()).orElse(null)
                : null);
        License savedLicense = licenseRepository.save(license);
        return ResponseEntity.ok(LicenseMapper.toDTO(savedLicense));
    }

    @PutMapping("/licenses/{id}")
    public ResponseEntity<LicenseDTO> updateLicense(@PathVariable Long id, @RequestBody LicenseDTO licenseDTO) {
        return licenseRepository.findById(id)
                .map(existingLicense -> {
                    if (licenseDTO.getKey() != null) existingLicense.setL01_KEY(licenseDTO.getKey());
                    if (licenseDTO.getStartDate() != null) existingLicense.setL01_START_DATE(licenseDTO.getStartDate());
                    if (licenseDTO.getEndDate() != null) existingLicense.setL01_END_DATE(licenseDTO.getEndDate());
                    existingLicense.setL01_REVOKED(licenseDTO.isRevoked());
                    existingLicense.setL01_EXTENDED(licenseDTO.isExtended());
                    if (licenseDTO.getCompanyId() != null) {
                        existingLicense.setCompany(companyRepository.findById(licenseDTO.getCompanyId()).orElse(null));
                    }
                    if (licenseDTO.getLicensePlanId() != null) {
                        existingLicense.setLicensePlan(licensePlanRepository.findById(licenseDTO.getLicensePlanId()).orElse(null));
                    }
                    return licenseRepository.save(existingLicense);
                })
                .map(LicenseMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        if (licenseRepository.existsById(id)) {
            licenseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // === Методы для Company ===

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO> companyDTOs = companies.stream()
                .map(CompanyMapper::toDTO)
                .toList();
        return ResponseEntity.ok(companyDTOs);
    }


    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id)
                .map(CompanyMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = CompanyMapper.toEntity(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return ResponseEntity.ok(CompanyMapper.toDTO(savedCompany));
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    if (companyDTO.getName() != null) existingCompany.setC01_NAME(companyDTO.getName());
                    if (companyDTO.getAddress() != null) existingCompany.setC01_ADRESS(companyDTO.getAddress());
                    if (companyDTO.getContact() != null) existingCompany.setC01_CONTACT(companyDTO.getContact());
                    if (companyDTO.getDescription() != null) existingCompany.setC01_DESC(companyDTO.getDescription());
                    return companyRepository.save(existingCompany);
                })
                .map(CompanyMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    // === Методы для LicensePlan ===

    @GetMapping("/license-plans")
    public ResponseEntity<List<LicensePlanDTO>> getAllLicensePlans() {
        List<LicensePlan> licensePlans = licensePlanRepository.findAll();
        List<LicensePlanDTO> licensePlanDTOs = licensePlans.stream()
                .map(LicensePlanMapper::toDTO)
                .toList();
        return ResponseEntity.ok(licensePlanDTOs);
    }


    @GetMapping("/license-plans/{id}")
    public ResponseEntity<LicensePlanDTO> getLicensePlanById(@PathVariable Long id) {
        return licensePlanRepository.findById(id)
                .map(LicensePlanMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/license-plans")
    public ResponseEntity<LicensePlanDTO> createLicensePlan(@RequestBody LicensePlanDTO licensePlanDTO) {
        LicensePlan licensePlan = LicensePlanMapper.toEntity(licensePlanDTO);
        LicensePlan savedLicensePlan = licensePlanRepository.save(licensePlan);
        return ResponseEntity.ok(LicensePlanMapper.toDTO(savedLicensePlan));
    }

    @PutMapping("/license-plans/{id}")
    public ResponseEntity<LicensePlanDTO> updateLicensePlan(@PathVariable Long id, @RequestBody LicensePlanDTO licensePlanDTO) {
        return licensePlanRepository.findById(id)
                .map(existingPlan -> {
                    if (licensePlanDTO.getName() != null) existingPlan.setLP01_NAME(licensePlanDTO.getName());
                    if (licensePlanDTO.getMaxUsers() != null) existingPlan.setLP01_MAX_USERS(licensePlanDTO.getMaxUsers());
                    if (licensePlanDTO.getPrice() != null) existingPlan.setLP01_PRICE(licensePlanDTO.getPrice());
                    return licensePlanRepository.save(existingPlan);
                })
                .map(LicensePlanMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/license-plans/{id}")
    public ResponseEntity<Void> deleteLicensePlan(@PathVariable Long id) {
        if (licensePlanRepository.existsById(id)) {
            licensePlanRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

