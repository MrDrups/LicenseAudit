package Licences.controller;

import Licences.config.CurrentUserProvider;
import Licences.model.Company;
import Licences.model.LicensePlan;
import Licences.model.LicenseRequest;
import Licences.model.User;
import Licences.service.CompanyService;
import Licences.service.LicensePlanService;
import Licences.service.LicenseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LicenseRequestController {

    private final LicenseRequestService licenseRequestService;
    private final CompanyService companyService;
    private final LicensePlanService licensePlanService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/requests")
    public String listRequests(@RequestParam(required = false) String keyword,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        List<LicenseRequest> requests;
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_LEAD".equals(userRole)) {
            // LEAD видит все заявки (не только pending)
            requests = licenseRequestService.getAllRequests(keyword);
        } else if ("ROLE_USER".equals(userRole)) {
            // USER видит только свои заявки
            User currentUser = currentUserProvider.getCurrentUser();
            requests = licenseRequestService.getRequestsByAuthor(currentUser);
        } else {
            // ADMIN видит все заявки
            requests = licenseRequestService.getAllRequests(keyword);
        }

        List<Company> companies = companyService.getAllCompanies(null);
        List<LicensePlan> licensePlans = licensePlanService.getAllLicenses(null);

        model.addAttribute("requests", requests);
        model.addAttribute("companies", companies);
        model.addAttribute("licensePlans", licensePlans);
        model.addAttribute("keyword", keyword);
        model.addAttribute("userRole", userRole);
        return "requests";
    }

    @GetMapping("/requests/create")
    public String showCreateForm(Model model) {
        List<Company> companies = companyService.getAllCompanies(null);
        List<LicensePlan> licensePlans = licensePlanService.getAllLicenses(null);
        model.addAttribute("companies", companies);
        model.addAttribute("licensePlans", licensePlans);
        model.addAttribute("licenseRequest", new LicenseRequest());
        return "request-create";
    }

    @PostMapping("/requests/create")
    public String createRequest(@ModelAttribute LicenseRequest licenseRequest,
                                @RequestParam Long companyId,
                                @RequestParam Long licensePlanId) {
        licenseRequestService.createRequest(licenseRequest, companyId, licensePlanId);
        return "redirect:/";
    }

    @PostMapping("/requests/approve/{id}")
    public String approveRequest(@PathVariable Long id) {
        licenseRequestService.approveRequest(id);
        return "redirect:/requests";
    }

    @PostMapping("/requests/reject/{id}")
    public String rejectRequest(@PathVariable Long id) {
        licenseRequestService.rejectRequest(id);
        return "redirect:/requests";
    }

    @GetMapping("/requests/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        LicenseRequest request = licenseRequestService.getRequestById(id);

        if (!LicenseRequest.STATUS_PENDING.equals(request.getStatus())) {
            return "redirect:/requests";
        }

        List<Company> companies = companyService.getAllCompanies(null);
        List<LicensePlan> licensePlans = licensePlanService.getAllLicenses(null);
        model.addAttribute("licenseRequest", request);
        model.addAttribute("companies", companies);
        model.addAttribute("licensePlans", licensePlans);
        return "request-edit";
    }

    @PostMapping("/requests/edit/{id}")
    public String editRequest(@PathVariable Long id,
                              @ModelAttribute LicenseRequest licenseRequest,
                              @RequestParam Long companyId,
                              @RequestParam Long licensePlanId) {
        licenseRequestService.updateRequest(id, licenseRequest, companyId, licensePlanId);
        return "redirect:/requests";
    }

    @PostMapping("/requests/cancel/{id}")
    public String cancelRequest(@PathVariable Long id) {
        licenseRequestService.cancelRequest(id);
        return "redirect:/requests";
    }
}
