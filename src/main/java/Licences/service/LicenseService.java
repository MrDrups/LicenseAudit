package Licences.service;

import Licences.model.Company;
import Licences.model.License;
import Licences.model.LicensePlan;
import Licences.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final LicensePlanRepository licensePlanRepository;
    private final CompanyRepository companyRepository;
    private final LicenseLogService licenseLogService;

    public void saveLicense(License license, Long companyId, Long licensePlanId) {
        License oldLicense = null;

        if (license.getID() != null) {
            oldLicense = licenseRepository.findById(license.getID()).orElse(null);
        }
        // Установка связей
        if (companyId != null) {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Компания с ID " + companyId + " не найдена"));
            license.setCompany(company);
        }

        if (licensePlanId != null) {
            LicensePlan licensePlan = licensePlanRepository.findById(licensePlanId)
                    .orElseThrow(() -> new IllegalArgumentException("Лицензионный план с ID " + licensePlanId + " не найден"));
            license.setLicensePlan(licensePlan);
        }

        License savedLicense = licenseRepository.save(license);

        // Записываем лог
        if (oldLicense != null) {
            licenseLogService.createLog("UPDATE", oldLicense, savedLicense);
        } else {
            licenseLogService.createLog("CREATE", null, savedLicense);
        }
    }

    public List<License> getAllLicenses(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return licenseRepository.findAll();
        }
        return licenseRepository.search(keyword);
    }

    public Optional<License> getLicenseById(Long id) {
        return licenseRepository.findById(id);
    }

    // Удалить лицензию
    public void deleteLicense(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Лицензия с ID " + id + " не найдена"));

        licenseLogService.createLog("DELETE", license, null);
        licenseRepository.deleteById(id);
    }
}