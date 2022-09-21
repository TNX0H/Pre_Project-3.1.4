//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import ru.kata.spring.boot_security.demo.model.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class UserController1 {
//
//    @GetMapping("/user")
//    public String pageForUser (@AuthenticationPrincipal User user, Model model) {
//        model.addAttribute("user", user);
//        return "index";
//    }
//
//    @GetMapping(value = "/login")
//    public String loginPage() {
//        return "index";
//    }
//}