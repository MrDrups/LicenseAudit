package Licences.service;

import Licences.model.Role;
import Licences.model.User;
import Licences.repository.RoleRepository;
import Licences.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String name, String login, String password, String email) {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        User user = new User();
        user.setNAME(name);
        user.setLOGIN(login);
        user.setPASS(passwordEncoder.encode(password));
        user.setEMAIL(email);
        user.setRole(role);
        userRepository.save(user);
    }

    public List<User> getAllUsers(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.search(keyword);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}