package Licences.service;

import Licences.model.LicenseLog;
import Licences.repository.LicenseLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseLogService {
    public final LicenseLogRepository licenseLogRepository;

    public List<LicenseLog> getAllLicenseLogs(){
        return licenseLogRepository.findAll();
    }

    public LicenseLog saveLicenseLog(LicenseLog licenseLog){
        return licenseLogRepository.save(licenseLog);
    }

    public void deleteById(long id){
        licenseLogRepository.deleteById(id);
    }
}
