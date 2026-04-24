package com.github.crimson95.psacms.service;

import com.github.crimson95.psacms.dto.UserCreateRequest;
import com.github.crimson95.psacms.entity.User;
import com.github.crimson95.psacms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // 告訴 Spring 這是一位負責商業邏輯的「主廚」
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 把剛剛在 SecurityConfig 建立的加密器「注入」進來
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 專門處理「註冊」這個商業邏輯的方法
    public User registerUser(UserCreateRequest request) {

        // 1. 這裡未來可以做各種邏輯檢查 (例如：if 帳號已存在，就拒絕註冊)

        // 2. 將 DTO 轉換為 Entity
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setRole(request.getRole());

        // 3. 魔法發生在這裡！呼叫 encode() 方法，它會自動生成 Salt 並進行單向加密
        String password = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(password);

        // 4. 將處理好的資料交給 Repository 存入資料庫
        return userRepository.save(newUser);
    }
}
