package sbs.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import sbs.web.models.UserProfile;
import sbs.web.models.Authorities;
import sbs.web.models.Users;

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
		List<UserProfile> users = userService.getAllUsers();
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
		model.addAttribute("user", new UserProfile());
		return "registeruser";
	}



	@RequestMapping(value="/signup")
	public String showRegister(Model model) {	
		model.addAttribute("externaluser", new UserProfile());
		return "signup";
	}
	

//	@RequestMapping(value="/logout")
//	public String logoutUser(Model model) {	
//		return "homepage";
//	}
	

	@RequestMapping(value = "/mylogin")
	public String loginUser(Model model) {
		System.out.println("loginUser");
		model.addAttribute("userlogin", new UserProfile());
		return "mylogin";
	}
	
	
	@RequestMapping(value = "/userconfirm")
	public String showUserConfirmation(Model model) {
		System.out.println("User Confirmation");
		model.addAttribute("users", new Users());
		return "userconfirm";
	}
	@RequestMapping(value = "/welcome")
	public String showWelcome() {
		System.out.println("SHOW WELCOME");
		return "welcome";
	}
	
	@RequestMapping(value = "/activateuser", method = RequestMethod.POST)
	public String ActivateUser(@Valid Users users, BindingResult result,  Principal principal) {
		String uname=principal.getName();
		System.out.println(uname);
//		if (result.hasErrors()) {
//			// model.addAttribute("user", user);
//			return "userconfirmation";
//		}
		users.setUsername(uname);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setEnabled(true);
		users.setCredentialsNonExpired(true);
		
		Authorities auth = new Authorities();
		auth.setUsername(uname);
		auth.setAuthority("ROLE_USER");
		userService.setAuthority(auth);

		userService.userActivation(users);
		return "homepage";
	}
	
	@RequestMapping(value = "/adminhome")
	public String adminHome(Model model) {
		
		System.out.println("Admin Home");
		return "adminhome";
	}

	@RequestMapping(value = "/employeecreation")
	public String createEmployee(@Valid UserProfile user,BindingResult result,Model model) {
		if(user!=null && user.getUsername() != null)
		{	
			System.out.println(user);
			UserProfile uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
			if (uniqueUser == null) {
				System.out.println(user);
				user.setIsnewuser(true);
				user.setCanlogin(false);
				
				userService.createUser(user);
				return "employeecreation";
			} else {
				System.out.println("Caught duplicate Username");
				result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
				return "employeecreation";
			}	
		}
		List<String> authorities = new ArrayList<>();
		authorities.add("ROLE_USER");
		authorities.add("ROLE_MANAGER");
		model.addAttribute("roles", authorities);
		model.addAttribute("user", new UserProfile());
		return "employeecreation";
	}


	@RequestMapping(value = "/registerbtn", method = RequestMethod.POST)

	public String RegisterUser(@Valid UserProfile user, BindingResult result) {

		if (result.hasErrors()) {
			return "registeruser";
		}

		UserProfile uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
		if (uniqueUser == null) {
			System.out.println(user);
			user.setIsnewuser(true);
			user.setCanlogin(false);
			
			userService.createUser(user);
			return "homepage";
		} else {
			System.out.println("Caught duplicate Username");
			result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
			return "registeruser";
		}
	}
}
