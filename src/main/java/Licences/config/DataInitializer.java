package Licences.config;

import Licences.model.Role;
import Licences.model.User;
import Licences.repository.RoleRepository;
import Licences.repository.UserRepository;
import Licences.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PermissionService permissionService;

    @Bean
    public CommandLineRunner initializeData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Инициализация разрешений
            var licensesView = permissionService.findOrCreate("LICENSES_VIEW", "Просмотр лицензий");
            var licensesEdit = permissionService.findOrCreate("LICENSES_EDIT", "Редактирование лицензий");
            var companiesView = permissionService.findOrCreate("COMPANIES_VIEW", "Просмотр компаний");
            var companiesEdit = permissionService.findOrCreate("COMPANIES_EDIT", "Редактирование компаний");
            var plansView = permissionService.findOrCreate("PLANS_VIEW", "Просмотр лицензионных планов");
            var plansEdit = permissionService.findOrCreate("PLANS_EDIT", "Редактирование лицензионных планов");
            var adminEdit = permissionService.findOrCreate("ADMIN_EDIT", "Доступ к администрированию");
            var rolesEdit = permissionService.findOrCreate("ROLES_EDIT", "Управление ролями и разрешениями");
            var requestCreate = permissionService.findOrCreate("REQUEST_CREATE", "Создание заявок");

            // Инициализация ролей с разрешениями
            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setNAME("ADMIN");
                return roleRepository.save(role);
            });
            adminRole.setPermissions(Set.of(licensesView, companiesView, plansView, adminEdit, rolesEdit));
            roleRepository.save(adminRole);

            Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
                Role role = new Role();
                role.setNAME("USER");
                return roleRepository.save(role);
            });
            userRole.setPermissions(Set.of(licensesView, companiesView, plansView, requestCreate));
            roleRepository.save(userRole);

            Role leadRole = roleRepository.findByName("LEAD").orElseGet(() -> {
                Role role = new Role();
                role.setNAME("LEAD");
                return roleRepository.save(role);
            });
            leadRole.setPermissions(Set.of(licensesView, licensesEdit, companiesView, companiesEdit, plansView, plansEdit));
            roleRepository.save(leadRole);

            // Инициализация root-пользователя
            Optional<User> rootUser = userRepository.findByLogin("root");
            if (rootUser.isEmpty()) {
                User user = new User();
                user.setNAME("Admin");
                user.setLOGIN("root");
                user.setPASS(passwordEncoder.encode("root"));
                user.setEMAIL("root@root.ru");
                user.setRole(adminRole);
                userRepository.save(user);
            }
        };
    }

    /**
     * Тестовые пользователи создаются только в профиле dev.
     * В продакшн-окружении эти учётные записи с тривиальными паролями недопустимы.
     */
    @Bean
    @Profile("dev")
    public CommandLineRunner initTestUsers(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Тестовый пользователь LEAD
            Optional<User> leadUser = userRepository.findByLogin("lead");
            if (leadUser.isEmpty()) {
                User user = new User();
                user.setNAME("Lead User");
                user.setLOGIN("lead");
                user.setPASS(passwordEncoder.encode("lead"));
                user.setEMAIL("lead@test.ru");
                user.setRole(roleRepository.findByName("LEAD").orElseThrow());
                userRepository.save(user);
            }

            // Тестовый пользователь USER
            Optional<User> testUser = userRepository.findByLogin("user");
            if (testUser.isEmpty()) {
                User user = new User();
                user.setNAME("Test User");
                user.setLOGIN("user");
                user.setPASS(passwordEncoder.encode("user"));
                user.setEMAIL("user@test.ru");
                user.setRole(roleRepository.findByName("USER").orElseThrow());
                userRepository.save(user);
            }
        };
    }
}
