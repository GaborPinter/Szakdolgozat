package com.example.AUTOKER3.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/")
	public String gotoWelcomePage() {
		return "home.html";
	}
	
//	private String getLoggedinUsername() {
//		Authentication authentication = 
//				SecurityContextHolder.getContext().getAuthentication();
//		return authentication.getName();
//	}
}
