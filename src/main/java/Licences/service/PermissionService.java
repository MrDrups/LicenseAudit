package Licences.service;

import Licences.model.Permission;
import Licences.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission findOrCreate(String name, String description) {
        return permissionRepository.findByName(name).orElseGet(() -> {
            Permission p = new Permission();
            p.setNAME(name);
            p.setDESCRIPTION(description);
            return permissionRepository.save(p);
        });
    }
}
