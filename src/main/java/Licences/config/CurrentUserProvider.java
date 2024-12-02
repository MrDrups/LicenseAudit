package Licences.config;

import Licences.model.User;
import Licences.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    private static UserRepository userRepository;

    public CurrentUserProvider(UserRepository userRepository) {
        CurrentUserProvider.userRepository = userRepository;
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return userRepository.findByLogin(username)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + username));
        }
        throw new RuntimeException("Не удалось определить текущего пользователя.");
    }
}
