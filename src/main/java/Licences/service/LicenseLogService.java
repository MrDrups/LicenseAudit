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
    private final CurrentUserProvider currentUserProvider;

    public void createLog(String changeType, License oldLicense, License newLicense) {
        createLog(changeType,
                oldLicense != null ? oldLicense.toString() : null,
                newLicense);
    }

    /**
     * Перегрузка для случаев, когда старое значение сохранено ДО мутации объекта.
     * Используется в revokeLicense/extendLicense, где JPA-сущность мутируется in-place.
     */
    public void createLog(String changeType, String oldValue, License newLicense) {
        User currentUser = currentUserProvider.getCurrentUser();
        LicenseLog log = new LicenseLog();
        log.setCHANGE_TYPE(changeType);
        log.setCHANGE_DATE(new Timestamp(System.currentTimeMillis()));
        log.setUser(currentUser);
        log.setLicense(newLicense);
        log.setOLD_VALUE(oldValue);
        if (newLicense != null) {
            log.setNEW_VALUE(newLicense.toString());
        }
        licenseLogRepository.save(log);
    }
}