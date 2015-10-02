package sbs.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	@RequestMapping("/random")
	public String showRandom() {
		System.out.println("random page");
		return "random";
	}
	// @RequestMapping("/forgotpassword")
	// public String showForgotPassword(Model model) {
	// System.out.println("forgotpassword page");
	// Users user = userService.getUserbyUsername("Arpit");
	// model.addAttribute("users", new User());
	// System.out.println(user);
	// return "forgotpassword";
	// }

	// @RequestMapping("/login")
	// public String showLogin() {
	// System.out.println("login page");
	// return "login";
	// }

	// @RequestMapping("/logout")
	// public String showLoggedOut() {
	// System.out.println("login page");
	// return "loggedout";
	// }

	@RequestMapping("/viewuser")
	public String showViewUser(Model model) {
		System.out.println("showViewUser");
		List<User> users = userService.getAllUsers();
		model.addAttribute("user", users);
		return "viewuser";
	}

	// @RequestMapping(value = "/mylogin")
	// public String loginUser(Model model) {
	// System.out.println("loginUser");
	// model.addAttribute("userlogin", new User());
	// return "mylogin";
	// }

	// @RequestMapping(value = "/loginbtn")
	// public String loginbtn(Model model, User user) {
	// System.out.println("loginbtn");
	// User validatedUser = userService.validateUser(user);
	//
	// if (validatedUser != null)
	//
	// {
	// String[] splits = validatedUser.getPassword().split(",");
	// String storedHash = splits[0];
	// String salt = splits[1];
	//
	// String hashedPwd = Utilities.hash_SHA(user.getPassword() + salt);
	//
	// System.out.println("validatedUser " + validatedUser);
	// if (hashedPwd.equalsIgnoreCase(storedHash)) {
	// System.out.println("Validated");
	// model.addAttribute("loggeduser", user);
	// return "viewuser";
	// }
	// }
	// model.addAttribute("userlogin", new User());
	// return "mylogin";
	// }

	@RequestMapping(value = "/registeruser")
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


	@RequestMapping(value = "/registerbtn", method = RequestMethod.POST)
	public String RegisterUser(@Valid User user, BindingResult result) {

		if (result.hasErrors()) {
			// model.addAttribute("user", user);
			return "registeruser";
		}

		User uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
		if (uniqueUser == null) {
			userService.createUser(user);
			return "homepage";
		}else{
			System.out.println("Caught duplicate Username");
			result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
			return "registeruser";
		}
		

		// String salt = Utilities.generateRandomAlphaNumeric();
		// String hashedPwd = Utilities.hash_SHA(user.getPassword()+salt);
		// String newPassword = hashedPwd+","+salt;
		// user.setPassword(newPassword);
		// userService.createUser(user);

	}

	// @RequestMapping(value = "/resetpasswordbtn", method = RequestMethod.POST)
	// public String resetPassword(Model model, @Valid Users users,
	// BindingResult result) {
	// Users user = userService.getUserbyUsername(users.getUsername());
	// System.out.println(user);
	// return "homepage";
	// }

}
