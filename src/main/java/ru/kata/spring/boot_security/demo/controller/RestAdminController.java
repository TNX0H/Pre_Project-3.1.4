package ru.kata.spring.boot_security.demo.controller;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Exception.UsernameExistException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RestAdminController {

    private final UserService userService;

    public RestAdminController(RoleService roleService, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<Exception> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new Exception(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UsernameExistException u) {
            throw new UsernameExistException("User with username exist");
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Exception> pageDelete(@PathVariable("id") long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(new Exception("User deleted"), HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }



    @PutMapping("/admin/{id}")
    public ResponseEntity<Exception> pageEdit(@PathVariable("id") long id,
                                                  @Valid @RequestBody User user,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new Exception(error), HttpStatus.BAD_REQUEST);
        }
        try {
            String oldPassword = userService.getUserById(id).getPassword();
            if (oldPassword.equals(user.getPassword())) {
                System.out.println("TRUE");
                user.setPassword(oldPassword);
                userService.updateUser(user);
            } else {
                System.out.println("FALSE");
                userService.addUser(user);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UsernameExistException u) {
            throw new UsernameExistException("User with username exist");
        }
    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}