package Licences.service;

import Licences.model.Permission;
import Licences.model.Role;
import Licences.repository.PermissionRepository;
import Licences.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public List<Role> getAllRoles(String keyword){
        if (keyword == null || keyword.isEmpty()) {
            return roleRepository.findAll();
        }
        return roleRepository.search(keyword);
    }

    @Transactional
    public void updateRolePermissions(Long roleId, Set<Long> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Роль с ID " + roleId + " не найдена"));

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(permissionIds));
        role.setPermissions(permissions);
        roleRepository.save(role);
    }
}
