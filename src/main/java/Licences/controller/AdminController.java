package Licences.controller;

import Licences.model.License;
import Licences.model.LicenseLog;
import Licences.model.Role;
import Licences.model.User;
import Licences.repository.*;
import Licences.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LicenseRepository licenseRepository;
    private final LicensePlanRepository licensePlanRepository;
    private final CompanyRepository companyRepository;
    private final LicenseLogRepository licenseLogRepository;

    @RequestMapping("/admin")
    public String viewAdminPanel(@RequestParam(required = false) String keyword, Model model) {
        List<LicenseLog> licenseLogs = licenseLogRepository.findAll();
        List<User> users = userService.getAllUsers(keyword);
        List<Role> roles = roleService.getAllRoles(keyword);
        long totalLicenses = licenseRepository.count();
        long totalCompanies = companyRepository.count();
        long totalLicensePlans = licensePlanRepository.count();
        List<Object[]> licensesByCompanies = licenseRepository.countByCompany();
        List<Object[]> licensesByPlans = licenseRepository.countLicensesByLicensePlan();
        for (LicenseLog log : licenseLogs) {
            if (log.getOLD_VALUE() != null) {
                log.setOLD_VALUE(formatLicense(log.getLicense()));
            } else {
                log.setOLD_VALUE("-----");
            }
            if (log.getNEW_VALUE() != null) {
                log.setNEW_VALUE(formatLicense(log.getLicense()));
            } else {
                log.setNEW_VALUE("-----");
            }
        }
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        model.addAttribute("licenseLogs", licenseLogs);
        model.addAttribute("totalLicenses", totalLicenses);
        model.addAttribute("totalCompanies", totalCompanies);
        model.addAttribute("totalLicensePlans", totalLicensePlans);
        model.addAttribute("licensesByCompanies", licensesByCompanies);
        model.addAttribute("licensesByPlans", licensesByPlans);
        return "admin";
    }

    @PostMapping("/admin/save")
    public String saveUser(@ModelAttribute User user, @RequestParam Long roleId) {
        Optional<User> existingUser = userRepository.findById(user.getID());
        Optional<Role> existingRole = roleRepository.findById(roleId);
        if (existingUser.isPresent() && existingRole.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setNAME(user.getNAME());
            updatedUser.setLOGIN(user.getLOGIN());
            updatedUser.setEMAIL(user.getEMAIL());
            updatedUser.setRole(existingRole.get());
            userRepository.save(updatedUser);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        // Заполняем форму для редактирования
        user.ifPresent(value -> model.addAttribute("user", value));
        return "admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteAdmin(@PathVariable long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    public String formatLicense(License license) {
        if (license == null) {
            return "-----";
        }
        return String.format("Ключ: %s, Компания: %s, Лиц.План: %s, Начало: %s, Конец: %s, Отозвана: %s, Продлена: %s", license.getKEY(), license.getCompany() != null ? license.getCompany().getNAME() : "-----", license.getLicensePlan() != null ? license.getLicensePlan().getNAME() : "-----", license.getSTART_DATE() != null ? license.getSTART_DATE().toString() : "-----", license.getEND_DATE() != null ? license.getEND_DATE().toString() : "-----", license.isREVOKED() ? "ДА" : "НЕТ", license.isEXTENDED() ? "ДА" : "НЕТ");
    }
}