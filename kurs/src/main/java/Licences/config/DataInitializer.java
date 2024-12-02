package Licences.config;

import Licences.model.Role;
import Licences.model.User;
import Licences.repository.RoleRepository;
import Licences.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = new Role();
            adminRole.setR01_NAME("ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setR01_NAME("USER");
            roleRepository.save(userRole);

            User admin = new User();
            admin.setU01_NAME("Admin");
            admin.setU01_LOGIN("admin");
            admin.setU01_PASS(passwordEncoder.encode("Topaz123$"));
            admin.setU01_EMAIL("Test@ya.ru");
            admin.setRole(adminRole);
            userRepository.save(admin);

            User baseUser = new User();
            baseUser.setU01_NAME("BaseUser");
            baseUser.setU01_LOGIN("abuser");
            baseUser.setU01_PASS(passwordEncoder.encode("Topaz123$"));
            baseUser.setU01_EMAIL("Test2@ya.ru");
            baseUser.setRole(userRole);
            userRepository.save(baseUser);
        };
    }
}
