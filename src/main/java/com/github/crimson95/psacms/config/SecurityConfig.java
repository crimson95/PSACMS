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

    // 註冊 BCrypt 密碼加密器，讓 Spring 把它放進容器裡備用
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 設定資安規則
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 暫時關閉 CSRF 防護（開發 API 時通常會先關閉，否則 Postman/HTTP Client 會被擋）
                .csrf(csrf -> csrf.disable())
                // 暫時允許所有的網址都可以直接訪問 (Permit All)，不需登入
                // 注意：等我們之後做 RBAC (角色權限) 時，這裡就會改成嚴格的控管規則！
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        
        return http.build();
    }
}
