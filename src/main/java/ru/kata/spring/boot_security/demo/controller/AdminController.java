package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    private void getUserRoles(User user) {
        user.setRoles(user.getRoles().stream()
        .map(role -> roleService.addRole(role.getUserRole()))
        .collect(Collectors.toSet()));
    }

    @GetMapping("admin")
    public String pageForAdmin(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRole());
        return "admin";
    }

    @GetMapping("admin/new")
    public String pageCreator(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roleService.findAllRole());
        return "create";
    }

    @PostMapping("admin/new")
    public String pageCreator(@ModelAttribute("user") User user){
        getUserRoles(user);
        userService.save(user);
        return "redirect:/admin";
    }



    @DeleteMapping("admin/delete/{id}")
    public String pageDelete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


    @PutMapping("admin/edit")
    public String pageEdit(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.findAllRole());
        getUserRoles(user);
        userService.update(user);
        return "redirect:/admin";
    }
}
