package com.springsecurity.springsecurity.controller;

import com.springsecurity.springsecurity.entity.User;
import com.springsecurity.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }





    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/user")
    public String userPage(Principal principal) {
        return "hello user" + principal.getName();
    }

    @GetMapping("/admin")
    public String adminPage(Principal principal) {
        return "hello admin" + principal.getName();
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {

        //можно преобразовать principal в user
        User user = userService.findByUsername(principal.getName());

        // Principal - авторизованный юзер
        return "secured part of web service: - " + user.getUsername() + " " + user.getEmail();
    }
}
