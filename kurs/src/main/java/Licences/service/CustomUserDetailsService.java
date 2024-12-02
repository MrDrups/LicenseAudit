package Licences.service;

import Licences.model.User;
import Licences.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String U01_LOGIN) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(U01_LOGIN)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return new org.springframework.security.core.userdetails.User(
                user.getU01_LOGIN(),
                user.getU01_PASS(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getR01_NAME()))
        );
    }
}
