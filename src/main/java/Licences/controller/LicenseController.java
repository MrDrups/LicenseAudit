package Licences.controller;

import Licences.model.*;
import Licences.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class LicenseController {
    private final LicenseService licenseService;
    private final CompanyService companyService;
    private final LicensePlanService licensePlanService;

    public LicenseController(LicenseService licenseService, CompanyService companyService,
                             LicensePlanService licensePlanService) {
        this.licenseService = licenseService;
        this.companyService = companyService;
        this.licensePlanService = licensePlanService;
    }


    @RequestMapping("/")
    public String viewLicenses(String keyword, Model model) {
        return getString(model, keyword);
    }

    @GetMapping("/search")
    public String searchLicense(Model model, String keyword){
        return getString(model, keyword);
    }

    private String getString(Model model, String keyword) {
        List<License> licenses = licenseService.getAllLicenses(keyword);
        List<Company> companies = companyService.getAllCompanies(keyword);
        List<LicensePlan> licensePlans = licensePlanService.getAllLicenses(keyword);

        model.addAttribute("licenses", licenses);
        model.addAttribute("companies", companies);
        model.addAttribute("licensePlans", licensePlans);
        return "index";
    }

    @PostMapping("/save")
    public String saveLicense(@ModelAttribute License license,
                              @RequestParam Long companyId,
                              @RequestParam Long licensePlanId) {
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
    public String deleteLicense(@PathVariable Long id, @AuthenticationPrincipal User user) {
        licenseService.deleteLicense(id, user);
        return "redirect:/";
    }
}
