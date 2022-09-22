//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping()
//    public String getUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        User user = userService.showUserByUsername(userDetails.getUsername());
//        model.addAttribute("users", userService.index());
//        model.addAttribute("user", user);
//        model.addAttribute("newUser", new User());
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "admin";
//    }
//
////    @GetMapping
////    public String getUsers(Model model) {
////        model.addAttribute("users", userService.index());
////        return "admin";
////    }
//
// //   @GetMapping("/new")
// //   public String newUser(Model model) {
// //       model.addAttribute("user", new User());
// //       return "new";
// //   }
//
//    @PostMapping("/new")
//    public String createUser(@ModelAttribute("user") User user, @RequestParam (value = "nameRoles", required = false) String roles)  {
//        user.setRoles(roleService.getByName(roles));
//        userService.addUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String edit(Model model, @PathVariable("id") long id){
//        model.addAttribute("user", userService.showUser(id));
//        return "edit";
//    }
//
//    @PatchMapping("/edit/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam (value = "nameRoles", required = false) String roles) {
//        user.setRoles(roleService.getByName(roles));
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String delete(@PathVariable("id") long id) {
//        userService.removeUser(id);
//        return "redirect:/admin";
//    }
//}
