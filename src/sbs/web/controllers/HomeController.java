package sbs.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sbs.web.models.User;
import sbs.web.service.UserService;

@Controller
public class HomeController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public String showhome(Model model) {
		System.out.println("showhome");
		return "homepage";
	}
	
	@RequestMapping("/viewuser")
	public String showViewUser(Model model) {
		System.out.println("showViewUser");
		List<User> users = userService.getAllUsers();
		model.addAttribute("user", users);
		return "viewuser";
	}
	
	@RequestMapping(value="/registeruser")
	public String showRegisterUser(Model model) {	
		System.out.println("showRegisterUser");
		model.addAttribute("user", new User());
		return "registeruser";
	}
	
	@RequestMapping(value="/logout")
	public String logoutUser(Model model) {	
		return "homepage";
	}
	
	@RequestMapping(value="/mylogin")
	public String loginUser(Model model) {	
		System.out.println("loginUser");
		model.addAttribute("userlogin", new User());
		return "mylogin";
	}

	@RequestMapping(value="/loginbtn")
	public String loginbtn(Model model,User user) {
		System.out.println("loginbtn");
		User validatedUser = userService.validateUser(user);
		System.out.println("validatedUser " +validatedUser);
		if(validatedUser != null){
			model.addAttribute("loggeduser", user);
			return "viewuser";
			}
		else
			return "mylogin";
			
	}

	
	@RequestMapping(value="/docreate",method=RequestMethod.POST)
	public String RegisterUser(Model model, User user) {	
		System.out.println("RegisterUser");
		userService.createUser(user);
		return "homepage";
	}
	

}
