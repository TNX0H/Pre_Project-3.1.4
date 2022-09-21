package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;



    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO,RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        addDefaultUser();
    }

    @Override
    public List<User> index() {
        return userDAO.getAllUsers();
    }

    @Override
    public User showUser(long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public User showUserByUsername(String username){
        return userDAO.getUserByLogin(username);
    }


    @Override
    public List<User>  getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public void removeUser(long id) {
        userDAO.removeUser(id);
    }

    @Override
    public User getUserByLogin(String username) {
        return userDAO.getUserByLogin(username);
    }

    @Override
    public void addDefaultUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.findById(1L));
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(roleService.findById(1L));
        roleSet2.add(roleService.findById(2L));
        User user1 = new User("Garry", "Potter", (byte) 27, "user1@mail.ru", "user", "1", roleSet);
        User user2 = new User("Steve", "Jobs", (byte) 52, "admin@mail.ru", "admin", "admin", roleSet2);
        addUser(user1);
        addUser(user2);
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByLogin(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }
}