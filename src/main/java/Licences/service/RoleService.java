package Licences.service;

import Licences.model.Role;
import Licences.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles(String keyword){
        if (keyword == null || keyword.isEmpty()) {
            return roleRepository.findAll();
        }
        return roleRepository.search(keyword);
    }
}