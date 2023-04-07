package com.example.AUTOKER3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.AUTOKER3.beans.User;
import com.example.AUTOKER3.beans.UserRegistrationDto;
import com.example.AUTOKER3.repository.UserRepository;
import com.example.AUTOKER3.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) throws Exception {
		String email = registrationDto.getEmail();
		User findByEmail = userRepository.findByEmail(email);
		if (findByEmail != null) {
			return "redirect:/registration?error";
		}
		userService.save(registrationDto);
		return "redirect:/registration?success";
	}
}
