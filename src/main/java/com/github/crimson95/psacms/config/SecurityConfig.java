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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Spring injects this filter because JwtAuthenticationFilter is annotated with @Component.
    // The filter runs on every request and tries to rebuild the logged-in user from the JWT.
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;  // Custom filter that validates JWT bearer tokens.

    // Register BCrypt password encoder as a Spring Bean
    // Other classes can inject PasswordEncoder without manually creating this object.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Exposes Spring Security's AuthenticationManager for the login endpoint.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configure Spring Security's request rules and filter chain.
    // Think of this method as the main security routing table for the backend.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection temporarily for API testing via Postman/HTTP Client
            .csrf(csrf -> csrf.disable())
            .cors(org.springframework.security.config.Customizer.withDefaults())  // Enable CORS rules defined below.

            // Define which endpoints are public and which require specific authorities.
            // These rules are checked after JwtAuthenticationFilter has populated the SecurityContext.
            .authorizeHttpRequests(auth -> auth
                // Keep the context root public so Render/GitHub Pages tests can verify the backend is awake.
                .requestMatchers("/").permitAll()

                // Login must be public because users need this endpoint to obtain a JWT.
                .requestMatchers("/api/auth/login").permitAll()

                // 1. Registration is public so new users can create an account.
                .requestMatchers("/api/users/register").permitAll()

                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Allow browser CORS preflight requests.

                // 2. Only users with the CITIZEN authority can submit and view their own applications.
                .requestMatchers("/api/applications/submit").hasAuthority("CITIZEN")
                .requestMatchers(HttpMethod.GET, "/api/applications/mine").hasAuthority("CITIZEN")

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow all frontend origins during local development.
        // In production, replace this with the real frontend origin.
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        // HTTP methods that the browser is allowed to call.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Headers the browser may send, especially JWT Authorization and JSON Content-Type.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);  // Allow credentialed cross-origin requests.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply this CORS policy to all API paths.
        return source;
    }
}
