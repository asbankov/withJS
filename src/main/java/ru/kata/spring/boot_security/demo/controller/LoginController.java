package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;


@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/login")
    String loginPage () {
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setAge(23);
        user.setFirstName("Anton");
        user.setLastName("Bankov");
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        Set<User> users = new HashSet<>();
        users.add(user);
        userRole.setUsers(users);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        userService.addFirst(user, userRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setAge(22);
        admin.setFirstName("Maria");
        admin.setLastName("Pirog");
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        users = new HashSet<> ();
        users.add(admin);
        adminRole.setUsers(users);
        roles = new HashSet<>();
        roles.add(adminRole);
        admin.setRoles(roles);
        userService.addFirst(admin, adminRole);

        return "login";
    }
}
