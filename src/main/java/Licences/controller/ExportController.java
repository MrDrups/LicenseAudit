package Licences.controller;

import Licences.model.Company;
import Licences.model.License;
import Licences.model.LicenseLog;
import Licences.model.LicensePlan;
import Licences.repository.CompanyRepository;
import Licences.repository.LicenseLogRepository;
import Licences.repository.LicensePlanRepository;
import Licences.repository.LicenseRepository;
import Licences.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/export")
public class ExportController {
    private final ExportService exportService;
    private final LicenseRepository licenseRepository;
    private final CompanyRepository companyRepository;
    private final LicensePlanRepository licensePlanRepository;
    private final LicenseLogRepository licenseLogRepository;

    private static final MediaType CSV_MEDIA_TYPE = new MediaType("text", "csv", java.nio.charset.StandardCharsets.UTF_8);

    // ========== Excel Export ==========

    @GetMapping("/licenses")
    public ResponseEntity<InputStreamResource> exportLicenses() throws IOException {
        List<License> licenses = licenseRepository.findAll();
        byte[] excelData = exportService.exportLicensesToExcel(licenses);
        ByteArrayInputStream in = new ByteArrayInputStream(excelData);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=licenses.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(in));
    }

    @GetMapping("/companies")
    public ResponseEntity<InputStreamResource> exportCompanies() throws IOException {
        List<Company> companies = companyRepository.findAll();
        byte[] excelData = exportService.exportCompaniesToExcel(companies);
        ByteArrayInputStream in = new ByteArrayInputStream(excelData);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=companies.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(in));
    }

    @GetMapping("/plans")
    public ResponseEntity<InputStreamResource> exportLicensePlans() throws IOException {
        List<LicensePlan> licensePlans = licensePlanRepository.findAll();
        byte[] excelData = exportService.exportLicensePlansToExcel(licensePlans);
        ByteArrayInputStream in = new ByteArrayInputStream(excelData);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=license_plans.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(in));
    }

    @GetMapping("/logs")
    public ResponseEntity<InputStreamResource> exportLicenseLogs() throws IOException {
        List<LicenseLog> licenseLogs = licenseLogRepository.findAll();
        byte[] excelData = exportService.exportLicenseLogsToExcel(licenseLogs);
        ByteArrayInputStream in = new ByteArrayInputStream(excelData);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=license_logs.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(in));
    }

    // ========== CSV Export ==========

    @GetMapping("/licenses/csv")
    public ResponseEntity<byte[]> exportLicensesCsv() {
        List<License> licenses = licenseRepository.findAll();
        byte[] csvData = exportService.exportLicensesToCsv(licenses);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=licenses.csv");
        return ResponseEntity.ok().headers(headers).contentType(CSV_MEDIA_TYPE).body(csvData);
    }

    @GetMapping("/companies/csv")
    public ResponseEntity<byte[]> exportCompaniesCsv() {
        List<Company> companies = companyRepository.findAll();
        byte[] csvData = exportService.exportCompaniesToCsv(companies);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=companies.csv");
        return ResponseEntity.ok().headers(headers).contentType(CSV_MEDIA_TYPE).body(csvData);
    }

    @GetMapping("/license-plans/csv")
    public ResponseEntity<byte[]> exportLicensePlansCsv() {
        List<LicensePlan> licensePlans = licensePlanRepository.findAll();
        byte[] csvData = exportService.exportLicensePlansToCsv(licensePlans);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=license_plans.csv");
        return ResponseEntity.ok().headers(headers).contentType(CSV_MEDIA_TYPE).body(csvData);
    }

    @GetMapping("/license-logs/csv")
    public ResponseEntity<byte[]> exportLicenseLogsCsv() {
        List<LicenseLog> licenseLogs = licenseLogRepository.findAll();
        byte[] csvData = exportService.exportLicenseLogsToCsv(licenseLogs);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=license_logs.csv");
        return ResponseEntity.ok().headers(headers).contentType(CSV_MEDIA_TYPE).body(csvData);
    }
}
