package com.example.AUTOKER3.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String gotoWelcomePage(Model model) {
		return "home.html";
	}
	
//	private String getLoggedinUsername() {
//		Authentication authentication = 
//				SecurityContextHolder.getContext().getAuthentication();
//		return authentication.getName();
//	}
}
