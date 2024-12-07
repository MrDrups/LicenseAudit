package Licences.config;

import Licences.model.Role;
import Licences.model.User;
import Licences.repository.RoleRepository;
import Licences.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initializeData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setR01_NAME("ADMIN");
                return roleRepository.save(role);
            });

            Optional<User> rootUser = userRepository.findByLogin("root");
            if (rootUser.isEmpty()) {
                User user = new User();
                user.setU01_NAME("Admin");
                user.setU01_LOGIN("root");
                user.setU01_PASS(passwordEncoder.encode("root"));
                user.setU01_EMAIL("root@root.ru");
                user.setRole(adminRole);
                userRepository.save(user);
            }
        };
    }
}