package Licences.service;

import Licences.model.License;
import Licences.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseService {
    public final LicenseRepository licenseRepository;

    public List<License> getAllLicenses(){
        return licenseRepository.findAll();
    }

    public License saveLicense(License license){
        return licenseRepository.save(license);
    }

    public void deleteById(long id){
        licenseRepository.deleteById(id);
    }
}
