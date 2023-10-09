package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebController {


    @GetMapping("/")
    public String index(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        System.out.println("Địa chỉ IP của máy khách: " + clientIp);
        return "index";
    }

    @GetMapping("/SignUp")
    public String SignUp() {
        return "SignUp";
    }

    @GetMapping("/SignIn")
    public String SignIn() {
        return "SignIn";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/sendfile")
    public String upload() {
        return "sendfile";
    }
}
