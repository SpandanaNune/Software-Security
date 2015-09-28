package sbs.web.controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sbs.web.models.User;
import sbs.web.service.UserService;
import sbs.web.utils.Utilities;

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
	
	@RequestMapping(value="/signup")
	public String showRegister(Model model) {	
		model.addAttribute("externaluser", new User());
		return "signup";
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
		
		if(validatedUser != null)
		
		{
		String[] splits = validatedUser.getPassword().split(",");
		String storedHash = splits[0];
		String salt = splits[1];
		
		String hashedPwd = Utilities.hash_SHA(user.getPassword()+salt);
		
		System.out.println("validatedUser " +validatedUser);
		if(hashedPwd.equalsIgnoreCase(storedHash))
		{
			System.out.println("Validated");
			model.addAttribute("loggeduser", user);
			return "viewuser";
			}
		}		
		model.addAttribute("userlogin",new User());	
		return "mylogin";	
	}

	
	@RequestMapping(value="/docreate",method=RequestMethod.POST)
	public String RegisterUser(Model model, User user) {	
		System.out.println("RegisterUser");
		
		String salt = Utilities.generateRandomAlphaNumeric();
		String hashedPwd = Utilities.hash_SHA(user.getPassword()+salt);
		String newPassword = hashedPwd+","+salt;
		user.setPassword(newPassword);
		userService.createUser(user);
		return "homepage";
	}
	
	

}
