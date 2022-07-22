package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

	private UserService userService;
	@Autowired
	public void setUserService (UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public String showUsers(Principal principal, ModelMap model) {
		//System.out.println("OK");
		User user = (User) userService.loadUserByUsername(principal.getName());
		List<User> users = userService.listUsers();
		model.addAttribute("users", users);
		model.addAttribute("adminUser", user);
		model.addAttribute("newUser", new User());
		model.addAttribute("roles", userService.listRoles());
		return "admin";
	}

	@GetMapping(value = "/add")
	public String addPage(ModelMap model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", userService.listRoles());
		return "add";
	}

	@PostMapping(value = "/add")
	public String addUser(ModelMap model, @ModelAttribute User user) {
		userService.add(user);
		return "redirect:/admin";
	}


	@GetMapping(value = "/edit/{id}")
	public String editPage(ModelMap model, @PathVariable long id) {
		User user = userService.getByID(id);
		model.addAttribute("user", user);
		model.addAttribute("roles", userService.listRoles());
		return "edit";
	}

	@PostMapping(value = "/edit/{id}")
	public String editUser(ModelMap model, @ModelAttribute User user, @PathVariable long id) {
		user.setId(id);
		userService.edit(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "/delete/{id}")
	public String deletePage(ModelMap model, @PathVariable long id) {
		User user = userService.getByID(id);
		System.out.println(user.getFirstName());
		userService.delete(user);
		return "redirect:/admin";
	}

}