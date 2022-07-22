package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    User getByID(long id);
    void edit(User user);
    void delete(User user);
    List<User> listUsers();
    void addFirst(User user, Role role);
    List<Role> listRoles();
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
