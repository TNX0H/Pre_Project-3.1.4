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

@Component
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("admin")
    public String pageForAdmin(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService. getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("admin/new")
    public String pageCreator(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("admin/new")
    public String pageCreator(@ModelAttribute("user")
                              @Valid User user, BindingResult bindingResult,
                              @RequestParam("listRoles") ArrayList<Long> roles){
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        if (userService.getUserByLogin(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("username", "username",
                    String.format("User witn name \"%s\" is already exsist!", user.getUsername())));
            return "admin";
        }
        user.setRoles(roleService.findByIdRoles(roles));
        userService.addUser(user);
        return "redirect:/admin";
    }



    @DeleteMapping("admin/delete/{id}")
    public String pageDelete(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }



    @PutMapping("admin/edit")
    public String pageEdit(@Valid User user, BindingResult bindingResult,
                           @RequestParam("listRoles") ArrayList<Long>roles) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        user.setRoles(roleService.findByIdRoles(roles));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
