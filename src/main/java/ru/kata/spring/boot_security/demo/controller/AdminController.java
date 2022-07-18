package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

	private UserServiceImpl userService;
	@Autowired
	public void setUserService (UserServiceImpl userService) {
		this.userService = userService;
	}

	@GetMapping()
	public String showUsers(ModelMap model) {
		List<User> users = userService.listUsers();
		model.addAttribute("users", users);
		return "admin";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addPage(ModelMap model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", userService.listRoles());
		return "add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(ModelMap model, @ModelAttribute User user) {
		userService.add(user);
		return "redirect:/admin";
	}


	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editPage(ModelMap model, @PathVariable long id) {
		User user = userService.getByID(id);
		model.addAttribute("user", user);
		model.addAttribute("roles", userService.listRoles());
		return "edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String editUser(ModelMap model, @ModelAttribute User user, @PathVariable long id) {
		user.setId(id);
		userService.edit(user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deletePage(ModelMap model, @PathVariable long id) {
		User user = userService.getByID(id);
		userService.delete(user);
		return "redirect:/admin";
	}

	/*@GetMapping(value = "/cars")
	public String printCars(@RequestParam(defaultValue = "5") int count, ModelMap model) {
		CarService carService = new CarServiceImpl();
		carService.add("Audi", 2013, 20000);
		carService.add("Opel", 2020, 10000);
		carService.add("Volkswagen", 2010, 100000);
		carService.add("BMW", 2019, 35000);
		carService.add("Mercedes", 2020, 30000);
		List<Car> cars = carService.carsList(count);
		model.addAttribute("cars", cars);
		return "cars";
	}*/
}