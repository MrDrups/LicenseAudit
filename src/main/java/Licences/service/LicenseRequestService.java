package Licences.service;

import Licences.config.CurrentUserProvider;
import Licences.model.*;
import Licences.repository.CompanyRepository;
import Licences.repository.LicensePlanRepository;
import Licences.repository.LicenseRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseRequestService {

    private final LicenseRequestRepository licenseRequestRepository;
    private final LicenseService licenseService;
    private final CompanyRepository companyRepository;
    private final LicensePlanRepository licensePlanRepository;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public LicenseRequest createRequest(LicenseRequest request, Long companyId, Long licensePlanId) {
        User currentUser = currentUserProvider.getCurrentUser();
        request.setAuthor(currentUser);
        request.setStatus(LicenseRequest.STATUS_PENDING);

        if (companyId != null) {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Компания с ID " + companyId + " не найдена"));
            request.setCompany(company);
        }
        if (licensePlanId != null) {
            LicensePlan plan = licensePlanRepository.findById(licensePlanId)
                    .orElseThrow(() -> new IllegalArgumentException("План с ID " + licensePlanId + " не найден"));
            request.setLicensePlan(plan);
        }

        return licenseRequestRepository.save(request);
    }

    public List<LicenseRequest> getPendingRequests() {
        return licenseRequestRepository.findByStatusOrderByCreatedAtDesc(LicenseRequest.STATUS_PENDING);
    }

    public List<LicenseRequest> getRequestsByAuthor(User author) {
        return licenseRequestRepository.findByAuthorOrderByCreatedAtDesc(author);
    }

    public List<LicenseRequest> getAllRequests(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return licenseRequestRepository.findAll();
        }
        return licenseRequestRepository.search(keyword);
    }

    public LicenseRequest getRequestById(Long id) {
        return licenseRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с ID " + id + " не найдена"));
    }

    @Transactional
    public LicenseRequest approveRequest(Long requestId) {
        LicenseRequest request = licenseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с ID " + requestId + " не найдена"));

        if (!LicenseRequest.STATUS_PENDING.equals(request.getStatus())) {
            throw new IllegalStateException("Заявка уже рассмотрена");
        }

        User reviewer = currentUserProvider.getCurrentUser();
        verifyLeadOrAdmin(reviewer);
        request.setReviewer(reviewer);
        request.setStatus(LicenseRequest.STATUS_APPROVED);
        request.setReviewedAt(new Timestamp(System.currentTimeMillis()));

        // Создаём лицензию в реестре
        License license = new License();
        license.setKEY(request.getKEY());
        license.setSTART_DATE(request.getSTART_DATE());
        license.setEND_DATE(request.getEND_DATE());
        license.setREVOKED(false);
        license.setEXTENDED(false);
        license.setCOMMENT(request.getCOMMENT());
        license.setNOTIFICATION_PERIOD(request.getNOTIFICATION_PERIOD());

        Long companyId = request.getCompany() != null ? request.getCompany().getID() : null;
        Long planId = request.getLicensePlan() != null ? request.getLicensePlan().getID() : null;
        licenseService.saveLicense(license, companyId, planId);

        return licenseRequestRepository.save(request);
    }

    @Transactional
    public LicenseRequest rejectRequest(Long requestId) {
        LicenseRequest request = licenseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с ID " + requestId + " не найдена"));

        if (!LicenseRequest.STATUS_PENDING.equals(request.getStatus())) {
            throw new IllegalStateException("Заявка уже рассмотрена");
        }

        User reviewer = currentUserProvider.getCurrentUser();
        verifyLeadOrAdmin(reviewer);
        request.setReviewer(reviewer);
        request.setStatus(LicenseRequest.STATUS_REJECTED);
        request.setReviewedAt(new Timestamp(System.currentTimeMillis()));

        return licenseRequestRepository.save(request);
    }

    @Transactional
    public LicenseRequest updateRequest(Long id, LicenseRequest updatedData, Long companyId, Long licensePlanId) {
        LicenseRequest request = licenseRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с ID " + id + " не найдена"));

        if (!LicenseRequest.STATUS_PENDING.equals(request.getStatus())) {
            throw new IllegalStateException("Можно редактировать только заявки в статусе PENDING");
        }

        verifyOwnershipOrLead(request);

        request.setKEY(updatedData.getKEY());
        request.setSTART_DATE(updatedData.getSTART_DATE());
        request.setEND_DATE(updatedData.getEND_DATE());
        request.setCOMMENT(updatedData.getCOMMENT());
        request.setNOTIFICATION_PERIOD(updatedData.getNOTIFICATION_PERIOD());

        if (companyId != null) {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Компания с ID " + companyId + " не найдена"));
            request.setCompany(company);
        }
        if (licensePlanId != null) {
            LicensePlan plan = licensePlanRepository.findById(licensePlanId)
                    .orElseThrow(() -> new IllegalArgumentException("План с ID " + licensePlanId + " не найден"));
            request.setLicensePlan(plan);
        }

        return licenseRequestRepository.save(request);
    }

    @Transactional
    public LicenseRequest cancelRequest(Long id) {
        LicenseRequest request = licenseRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с ID " + id + " не найдена"));

        if (!LicenseRequest.STATUS_PENDING.equals(request.getStatus())) {
            throw new IllegalStateException("Можно отменить только заявку в статусе PENDING");
        }

        verifyOwnershipOrLead(request);

        request.setStatus(LicenseRequest.STATUS_CANCELLED);
        return licenseRequestRepository.save(request);
    }

    public List<LicenseRequest> getAllRequestsExcludingCancelled() {
        return licenseRequestRepository.findByStatusNotOrderByCreatedAtDesc(LicenseRequest.STATUS_CANCELLED);
    }

    /**
     * USER может редактировать/отменять только свои заявки.
     * LEAD и ADMIN могут работать с любыми заявками.
     */
    private void verifyOwnershipOrLead(LicenseRequest request) {
        User currentUser = currentUserProvider.getCurrentUser();
        boolean isLeadOrAdmin = currentUser.getRole() != null &&
                ("LEAD".equals(currentUser.getRole().getNAME()) || "ADMIN".equals(currentUser.getRole().getNAME()));
        if (!isLeadOrAdmin && currentUser.getID() != request.getAuthor().getID()) {
            throw new IllegalStateException("Вы можете изменять только свои заявки");
        }
    }

    /**
     * Одобрять/отклонять заявки могут только LEAD и ADMIN.
     */
    private void verifyLeadOrAdmin(User user) {
        boolean isLeadOrAdmin = user.getRole() != null &&
                ("LEAD".equals(user.getRole().getNAME()) || "ADMIN".equals(user.getRole().getNAME()));
        if (!isLeadOrAdmin) {
            throw new IllegalStateException("Только LEAD или ADMIN может рассматривать заявки");
        }
    }
}
