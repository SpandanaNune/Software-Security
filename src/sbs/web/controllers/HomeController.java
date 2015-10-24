package sbs.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sbs.web.models.Authorities;
import sbs.web.models.Authorities;
import sbs.web.models.PII;
import sbs.web.models.User;
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

	@RequestMapping("/Sample")
	public String showRandom() {
		System.out.println("random page");
		return "Sample";
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

	

	@RequestMapping(value = "/signup")
	public String showRegister(Model model) {
		model.addAttribute("externaluser", new User());
		return "signup";
	}

	// @RequestMapping(value="/logout")
	// public String logoutUser(Model model) {
	// return "homepage";
	// }

	@RequestMapping(value = "/mylogin")
	public String loginUser(Model model) {
		System.out.println("loginUser");
		model.addAttribute("userlogin", new User());
		return "mylogin";
	}

	@RequestMapping(value = "/registeruser")
	public String showRegisterUser(Model model) {
		System.out.println("showRegisterUser");
		model.addAttribute("user", new User());
		return "registeruser";
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
	public String ActivateUser(@Valid Users users, BindingResult result, Principal principal) {
		String uname = principal.getName();
		System.out.println(uname);
		// if (result.hasErrors()) {
		// // model.addAttribute("user", user);
		// return "userconfirmation";
		// }
		users.setUsername(uname);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setEnabled(true);
		users.setCredentialsNonExpired(true);

		Authorities auth = new Authorities();
		auth.setUsername(uname);
		auth.setAuthority("ROLE_USER");
		userService.setAuthority(auth);

		userService.saveOrUpdateUsers(users);
		return "homepage";
	}

	@RequestMapping(value = "/adminhome")
	public String adminHome(Model model) {

		System.out.println("Admin Home");
		return "adminhome";
	}
	@RequestMapping(value = "/pii")
	public String listPIIs(Model model)
	{
		List<PII> piis = userService.getAllPIIs();
		model.addAttribute("piis", piis);
		return "pii";
	}
	
	@RequestMapping("/acceptpii")
	public String acceptUserPII(Model model, @RequestParam("Accept") String username) {
		System.out.println("accepted "+username);
		userService.approvePII(username);
		List<PII> piis = userService.getAllPIIs();
		model.addAttribute("piis", piis);
		return "pii";
	}
	@RequestMapping("/declinepii")
	public String declineUserPII(Model model, @RequestParam("Decline") String username) {
		System.out.println("declined "+username);
		userService.deletePII(username);
		List<PII> piis = userService.getAllPIIs();
		model.addAttribute("piis", piis);
		return "pii";

	}

	@RequestMapping(value = "/employeecreation")
	public String createEmployee(HttpServletRequest rqst, @Valid User user,BindingResult result,Model model) {
		if(user!=null && user.getUsername() != null)
		{	
			String role = rqst.getParameter("role");
			System.out.println(user);
			User uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
			if (uniqueUser == null) {
				System.out.println(user);
				user.setIsnewuser(true);				
				userService.createUser(user);
				
				Authorities auth = new Authorities();
				auth.setUsername(user.getUsername());
				auth.setAuthority(role);
				userService.setAuthority(auth);
			} else {
				System.out.println("Caught duplicate Username");
				result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
			}	
		}
		List<String> authorities = new ArrayList<>();
		authorities.add("ROLE_EMPLOYEE");
		authorities.add("ROLE_MANAGER");
		model.addAttribute("roles", authorities);
		model.addAttribute("user", new User());
		return "employeecreation";
	}


	@RequestMapping(value = "/registerbtn", method = RequestMethod.POST)

	public String RegisterUser(Model model, @Valid User user, BindingResult result) {
		System.out.println("Finding errors, " + result.toString());
		if (result.hasErrors()) {
			System.out.println("It has errors");
			return "registeruser";
		}

		// UserProfile uniqueUser =
		// (userService.getUserregisterbyUsername(user.getUsername()));
		// if (uniqueUser == null) {
		// System.out.println(user);
		// user.setIsnewuser(true);
		// user.setCanlogin(false);
		//
		System.out.println(user.toString());
		userService.createUser(user);
		return "homepage";
		// } else {
		// System.out.println("Caught duplicate Username");
		// result.rejectValue("username", "DuplicateKeyException.user.username",
		// "Username already exists.");
		// return "registeruser";
		// }
	}
}
