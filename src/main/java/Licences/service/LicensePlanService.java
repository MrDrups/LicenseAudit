package Licences.service;

import Licences.model.LicensePlan;
import Licences.repository.LicensePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicensePlanService {
    public final LicensePlanRepository licensePlanRepository;

    public List<LicensePlan> getAllLicenses(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return licensePlanRepository.findAll();
        }
        return licensePlanRepository.search(keyword);
    }

    public Optional<LicensePlan> getLicensePlanById(Long id) {
        return licensePlanRepository.findById(id);
    }

    public void saveLicensePlan(LicensePlan licensePlan) {
        if (licensePlan.getID() != null && licensePlan.getID() > 0) {
            licensePlanRepository.save(licensePlan);
        } else {
            licensePlanRepository.save(licensePlan);
        }
    }

    public void deleteById(Long id){
        licensePlanRepository.deleteById(id);
    }
}