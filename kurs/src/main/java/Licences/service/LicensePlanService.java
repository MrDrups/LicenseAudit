package Licences.service;

import Licences.model.LicensePlan;
import Licences.repository.LicensePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicensePlanService {
    public final LicensePlanRepository licensePlanRepository;

    public List<LicensePlan> getAllLicensePlans(){
        return licensePlanRepository.findAll();
    }

    public LicensePlan saveLicensePlan(LicensePlan licensePlan){
        return licensePlanRepository.save(licensePlan);
    }

    public void deleteById(long id){
        licensePlanRepository.deleteById(id);
    }
}

