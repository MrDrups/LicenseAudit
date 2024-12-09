package Licences.controller;

import Licences.model.Company;
import Licences.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CompaniesController {
    private final CompanyService companyService;

    @RequestMapping("/companies")
    public String viewAllCompanies(@RequestParam(required = false) String keyword, Model model) {
        List<Company> companies = companyService.getAllCompanies(keyword);
        model.addAttribute("companies", companies);
        model.addAttribute("company", new Company());
        model.addAttribute("keyword", keyword);
        return "companies";
    }

    @PostMapping("/companies/save")
    public String saveCompany(@ModelAttribute("company") Company company) {
        if (company.getID() != null && company.getID() > 0) {
            companyService.saveCompany(company);
        } else {
            companyService.saveCompany(company);
        }
        return "redirect:/companies";
    }

    @GetMapping("/companies/edit/{id}")
    public String editCompany(@PathVariable("id") long id, Model model) {
        Optional<Company> company = companyService.getCompanyById(id);
        company.ifPresent(value -> model.addAttribute("company", value));
        return "companies";
    }

    @GetMapping("/companies/delete/{id}")
    public String deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }
}