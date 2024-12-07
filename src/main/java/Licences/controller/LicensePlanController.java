package Licences.controller;

import Licences.model.LicensePlan;
import Licences.service.LicensePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LicensePlanController {
    private final LicensePlanService licensePlanService;

    @RequestMapping("/license_plans")
    public String viewAllLicensePlans(@RequestParam(required = false) String keyword, Model model) {
        List<LicensePlan> licensePlans = licensePlanService.getAllLicenses(keyword);
        model.addAttribute("licensePlans", licensePlans);
        model.addAttribute("licensePlan", new LicensePlan());
        return "licensePlans";
    }

    @PostMapping("/license_plans/save")
    public String saveLicensePlan(@ModelAttribute("licensePlan") LicensePlan licensePlan) {
        if (licensePlan.getLP01_ID() != null && licensePlan.getLP01_ID() > 0) {
            licensePlanService.saveLicensePlan(licensePlan);
        } else {
            licensePlanService.saveLicensePlan(licensePlan);
        }
        return "redirect:/license_plans";
    }

    @GetMapping("/license_plans/edit/{id}")
    public String editLicensePlan(@PathVariable("id") long id, Model model) {
        Optional<LicensePlan> licensePlan = licensePlanService.getLicensePlanById(id);
        licensePlan.ifPresent(plan -> model.addAttribute("licensePlan", plan));
        return "licensePlans";
    }

    @GetMapping("/license_plans/delete/{id}")
    public String deleteLicensePlan(@PathVariable Long id) {
        licensePlanService.deleteById(id);
        return "redirect:/license_plans";
    }
}