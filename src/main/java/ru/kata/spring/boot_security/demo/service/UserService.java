package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User getUserById(long id);
    void addUser(User user);
    void removeUser(long id);
    void updateUser(@Valid User user);
    User getUserByLogin(String username);
    void addDefaultUser();
    User passwordCoder(User user);
}