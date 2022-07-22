package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value="/rest")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers () {
        List<User> users = userService.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> showAllRoles () {
        List<Role> roles = userService.listRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> addUser (@RequestBody User user) {
        System.out.println(user.getFirstName() + user.getLastName() + user.getUsername());
        userService.add(user);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable long id) {
        userService.delete(userService.getByID(id));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/idroles/{id}")
    public ResponseEntity<List<Role>> idRoles (@PathVariable long id) {
        Set<Role> roles = userService.getByID(id).getRoles();
        for (Role role : roles) {
            System.out.println(role.getPrintRole());
        }
        return new ResponseEntity<List<Role>>(new ArrayList<Role>(roles), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> editUser (@RequestBody User user) {
        userService.edit(user);
        return new ResponseEntity<> (HttpStatus.OK);
    }
}
