package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
        addDefaultRole();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public void addRole(Role role) {
        roleDAO.addRole(role);

    }
    @Override
    @PostConstruct
    public void addDefaultRole() {
        roleDAO.addRole(new Role("ROLE_USER"));
        roleDAO.addRole(new Role("ROLE_ADMIN"));
    }

    @Override
    public Role findById(long id) {
        return roleDAO.findById(id);
    }

    @Override
    public Set<Role> findByIdRoles(List<Long> roles) {
        return new HashSet<>(roleDAO.findByIdRoles(roles));
    }

    @Override
    public Set<Role> getByName(String name) {
        return roleDAO.findRoleByName(name);
    }
}