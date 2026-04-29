package com.github.crimson95.psacms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Register BCrypt password encoder as a Spring Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure security rules and filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection temporarily for API testing via Postman/HTTP Client
                .csrf(csrf -> csrf.disable())
                // Define which endpoints are public and which require specific authorities.
                .authorizeHttpRequests(auth -> auth
                    // 1. Registration is public so new users can create an account.
                    .requestMatchers("/api/users/register").permitAll()

                    // 2. Only users with the CITIZEN authority can submit applications.
                    .requestMatchers("/api/applications/submit").hasAuthority("CITIZEN")

                    // 3. Only users with the OFFICER authority can review application lists and status changes.
                    .requestMatchers("/api/applications/status/**").hasAuthority("OFFICER")

                    // 4. Any other request must come from an authenticated user.
                    .anyRequest().authenticated()
                )
                // Enable HTTP Basic authentication so API tools can send username/password directly.
                .httpBasic(org.springframework.security.config.Customizer.withDefaults());
        
        return http.build();
    }
}
