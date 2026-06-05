package Licences.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;

@Configuration
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF включён для веб-форм, отключён только для API-эндпоинтов
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(auth -> auth
                        // API GET — доступно всем авторизованным
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                        // API POST/PUT/DELETE — только LEAD и ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("LEAD", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("LEAD", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("LEAD", "ADMIN")
                        // Администрирование — только ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // CRUD лицензий — только LEAD
                        .requestMatchers("/save", "/delete/", "/edit/").hasRole("LEAD")
                        // Отзыв и продление лицензий — только LEAD
                        .requestMatchers("/licenses/revoke/", "/licenses/extend/").hasRole("LEAD")
                        // CRUD компаний — только LEAD
                        .requestMatchers("/companies/save", "/companies/delete/", "/companies/edit/").hasRole("LEAD")
                        // CRUD лицензионных планов — только LEAD
                        .requestMatchers("/license_plans/save", "/license_plans/delete/", "/license_plans/edit/").hasRole("LEAD")
                        // Заявки: одобрение/отклонение — только LEAD
                        .requestMatchers("/requests/approve/**", "/requests/reject/**").hasRole("LEAD")
                        // Заявки: просмотр, создание, редактирование, отмена — все три роли
                        .requestMatchers("/requests/**").hasAnyRole("USER", "LEAD", "ADMIN")
                        // Просмотр основных блоков — все три роли
                        .requestMatchers("/", "/companies", "/license_plans").hasAnyRole("USER", "LEAD", "ADMIN")
                        // Swagger — открыт
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs*/**", "/webjars/**").permitAll()
                        // Логин, регистрация, статические ресурсы — открыты
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/favicon.ico").permitAll()
                        // Всё остальное — требует аутентификацию
                        .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout").permitAll()
                )
                // JSON-ответы для API при 401/403
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                response.getWriter().write(objectMapper.writeValueAsString(
                                        Map.of("error", "Unauthorized", "status", 401)));
                            } else {
                                response.sendRedirect("/login");
                            }
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                response.getWriter().write(objectMapper.writeValueAsString(
                                        Map.of("error", "Forbidden", "status", 403)));
                            } else {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}