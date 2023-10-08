package com.springsecurity.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/user")
    public String userPage() {
        return "hello user";
    }


    @GetMapping("/admin")
    public String adminPage() {
        return "hello admin";
    }


    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {

        // Principal - авторизованный юзер
        return "secured part of web service: - " + principal.getName();
    }
}
