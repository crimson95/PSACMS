package com.github.crimson95.psacms.controller;

import com.github.crimson95.psacms.dto.UserCreateRequest;
import com.github.crimson95.psacms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")  // 這支 Controller 底下的網址都會以 /api/users 開頭
public class UserController {

    // 這就是傳說中的「依賴注入 (DI)」
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserCreateRequest request){

        // 服務生只做兩件事：
        // 1. 把客人的單子 (request) 交給主廚 (userService) 去處理
        userService.registerUser(request);

        // 2. 把結果端給客人
        return "Registration successful! User [" + request.getUsername() + "] has been securely saved.";
    }
}
