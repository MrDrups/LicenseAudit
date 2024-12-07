package Licences.service;

import Licences.config.CurrentUserProvider;
import Licences.model.License;
import Licences.model.LicenseLog;
import Licences.model.User;
import Licences.repository.LicenseLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class LicenseLogService {
    private final LicenseLogRepository licenseLogRepository;

    public void createLog(String changeType, License oldLicense, License newLicense) {
        User currentUser = CurrentUserProvider.getCurrentUser();
        LicenseLog log = new LicenseLog();
        log.setLL01_CHANGE_TYPE(changeType);
        log.setLL01_CHANGE_DATE(new Timestamp(System.currentTimeMillis()));
        log.setUser(currentUser);
        log.setLicense(newLicense != null ? newLicense : oldLicense);

        if (oldLicense != null) {
            log.setLL01_OLD_VALUE(oldLicense.toString());
        }

        if (newLicense != null) {
            log.setLL01_NEW_VALUE(newLicense.toString());
        }

        licenseLogRepository.save(log);
    }
}