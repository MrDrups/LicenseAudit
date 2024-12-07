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
            if (log.getLL01_OLD_VALUE() != null) {
                log.setLL01_OLD_VALUE(formatLicense(log.getLicense()));
            } else {
                log.setLL01_OLD_VALUE("-----");
            }
            if (log.getLL01_NEW_VALUE() != null) {
                log.setLL01_NEW_VALUE(formatLicense(log.getLicense()));
            } else {
                log.setLL01_NEW_VALUE("-----");
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
        Optional<User> existingUser = userRepository.findById(user.getU01_ID());
        Optional<Role> existingRole = roleRepository.findById(roleId);
        if (existingUser.isPresent() && existingRole.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setU01_NAME(user.getU01_NAME());
            updatedUser.setU01_LOGIN(user.getU01_LOGIN());
            updatedUser.setU01_EMAIL(user.getU01_EMAIL());
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
        return String.format("Ключ: %s, Компания: %s, Лиц.План: %s, Начало: %s, Конец: %s, Отозвана: %s, Продлена: %s", license.getL01_KEY(), license.getCompany() != null ? license.getCompany().getC01_NAME() : "-----", license.getLicensePlan() != null ? license.getLicensePlan().getLP01_NAME() : "-----", license.getL01_START_DATE() != null ? license.getL01_START_DATE().toString() : "-----", license.getL01_END_DATE() != null ? license.getL01_END_DATE().toString() : "-----", license.isL01_REVOKED() ? "ДА" : "НЕТ", license.isL01_EXTENDED() ? "ДА" : "НЕТ");
    }
}