package Licences.service;

import Licences.model.License;
import Licences.model.Role;
import Licences.model.User;
import Licences.repository.RoleRepository;
import Licences.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String name, String login, String password, String email) {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        User user = new User();
        user.setU01_NAME(name);
        user.setU01_LOGIN(login);
        user.setU01_PASS(passwordEncoder.encode(password));
        user.setU01_EMAIL(email);
        user.setRole(role);

        userRepository.save(user);
    }

    public List<User> getAllUsers(String keyword) {
        System.out.println("Вызов getAllLicenses с параметром: " + keyword);
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
