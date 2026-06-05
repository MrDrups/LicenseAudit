package Licences.controller;

import Licences.model.*;
import Licences.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;
    private final CompanyService companyService;
    private final LicensePlanService licensePlanService;

    @RequestMapping("/")
    public String viewLicenses(String keyword, Model model) {
        return getString(model, keyword);
    }

    private String getString(Model model, String keyword) {
        List<License> licenses = licenseService.getAllLicenses(keyword);
        List<Company> companies = companyService.getAllCompanies(keyword);
        List<LicensePlan> licensePlans = licensePlanService.getAllLicenses(keyword);
        model.addAttribute("licenses", licenses);
        model.addAttribute("companies", companies);
        model.addAttribute("licensePlans", licensePlans);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @PostMapping("/save")
    public String saveLicense(@ModelAttribute License license,
                              @RequestParam Long companyId,
                              @RequestParam Long licensePlanId) {
        Company company = companyService.getCompanyById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Компания с ID " + companyId + " не найдена"));
        license.setCompany(company);

        licenseService.saveLicense(license, companyId, licensePlanId);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editLicense(@PathVariable("id") long id, Model model) {
        Optional<License> license = licenseService.getLicenseById(id);
        license.ifPresent(value -> model.addAttribute("license", value));
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicense(id);
        return "redirect:/";
    }

    @PostMapping("/licenses/revoke/{id}")
    public String revokeLicense(@PathVariable Long id) {
        licenseService.revokeLicense(id);
        return "redirect:/";
    }

    @PostMapping("/licenses/extend/{id}")
    public String extendLicense(@PathVariable Long id, @RequestParam LocalDate newEndDate) {
        licenseService.extendLicense(id, newEndDate);
        return "redirect:/";
    }
}
