package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserServiceImpl;

import javax.validation.Valid;


@Controller
@RequestMapping("/")
public class UserController {
	private final UserServiceImpl userService;

	@Autowired
	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}


	@GetMapping()
	public String index(ModelMap model) {
		model.addAttribute("users", userService.findAll());
		return "index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, ModelMap model) {
		model.addAttribute("user", userService.findOne(id));
		return "show";
	}

	@GetMapping("/new")
	public String newPerson(@ModelAttribute("user") User user) {
		return "new";
	}

	@PostMapping()
	public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new";
		}
		userService.save(user);
		return "redirect:/";
	}

	@GetMapping("/{id}/edit")
	public String edit(ModelMap model, @PathVariable("id") int id) {
		model.addAttribute("user", userService.findOne(id));
		return "edit";
	}

	@PatchMapping("/{id}")
	public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
						 @PathVariable("id") int id) {
		if (bindingResult.hasErrors()) {
			return "edit";
		}
		userService.update(id, user);
		return "redirect:/";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:/";
	}
}