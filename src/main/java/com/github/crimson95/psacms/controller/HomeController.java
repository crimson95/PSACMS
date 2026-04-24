package com.github.crimson95.psacms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // 告訴 Spring 這是一個負責處理 HTTP 請求的控制器
public class HomeController {

    @GetMapping("/")  // 當瀏覽器訪問根目錄 "/" 時，執行這個方法
    public String home() {
        return "Welcome to PSACMS. The server and database are functional.";
    }
}
