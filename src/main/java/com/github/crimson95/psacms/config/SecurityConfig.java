package com.github.crimson95.psacms.config;

import com.github.crimson95.psacms.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;  // Custom filter that validates JWT bearer tokens.

    // Register BCrypt password encoder as a Spring Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Exposes Spring Security's AuthenticationManager for the login endpoint.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configure security rules and filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection temporarily for API testing via Postman/HTTP Client
            .csrf(csrf -> csrf.disable())

            // Define which endpoints are public and which require specific authorities.
            .authorizeHttpRequests(auth -> auth
                // Login must be public because users need this endpoint to obtain a JWT.
                .requestMatchers("/api/auth/login").permitAll()

                // 1. Registration is public so new users can create an account.
                .requestMatchers("/api/users/register").permitAll()

                // 2. Only users with the CITIZEN authority can submit applications.
                .requestMatchers("/api/applications/submit").hasAuthority("CITIZEN")

                // 3. Only users with the OFFICER authority can review application lists and status changes.
                .requestMatchers("/api/applications/status/**").hasAuthority("OFFICER")
                .requestMatchers(HttpMethod.PUT, "/api/applications/*/status").hasAuthority("OFFICER")

                // 4. Any other request must come from an authenticated user.
                .anyRequest().authenticated()
            )
            // JWT authentication is stateless; each request must carry its own token.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Run the JWT filter before Spring's username/password authentication filter.
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
