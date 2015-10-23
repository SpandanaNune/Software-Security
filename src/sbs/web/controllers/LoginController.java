package sbs.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sbs.web.models.Authorities;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;

@Controller
public class LoginController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/login")
	public String showLogin() {
		System.out.println("login page");
		return "login";
	}

	@RequestMapping("/logout")
	public String showLoggedOut() {
		System.out.println("login page");
		return "loggedout";
	}

	@RequestMapping("/forgotpassword")
	public String showForgotPassword(Model model) {
		System.out.println("forgotpassword page");
		Users user = userService.getUserbyUsername("Arpit");
		model.addAttribute("users", new Users());
		System.out.println(user);
		return "forgotpassword";
	}

	@RequestMapping(value = "/resetpasswordbtn", method = RequestMethod.POST)
	public String resetPassword(Model model, @Valid Users users, BindingResult result) {
		Users user = userService.getUserbyUsername(users.getUsername());
		System.out.println(user);
		return "homepage";
	}

	@RequestMapping("/systemadmin")
	public String showAdminHome() {
		return "adminhome";
	}
	
/*************************************SYSTEM**MANAGER*******************************/
	
	@RequestMapping("/manager")
	public String showManagerHome(Model model) {
//		List<User> user = userService.getAllNewUsers();
//		model.addAttribute("user", user);
		return "managerhome";
	}
	
	@RequestMapping("/usersignuprequest")
	public String showUserSignUpRequest(Model model) {
		List<User> user = userService.getAllNewUsers();
		model.addAttribute("user", user);
		return "usersignuprequest";
	}
	
	@RequestMapping("/declinebtn")
	public String deleteUserSignUp(Model model, @RequestParam("Decline") String username) {
		System.out.println("Delete Button Operation");
		System.out.println(username);
		
		userService.deleteUserRequest(username);
		List<User> updateduser = userService.getAllNewUsers();
		
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
		
	}	
	@RequestMapping("/acceptbtn")
	public String acceptUserSignUp(Model model, @RequestParam("Accept") String username) {
	 
		User user = userService.getUserregisterbyUsername(username);
		user.setCanlogin(true);
		user.setIsnewuser(false);
		userService.createUser(user);
		System.out.println("hello"+user);

		Users users = new Users();
		users.setUsername(username);
		users.setPassword("45678");
		users.setEnabled(true);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setCredentialsNonExpired(true);

		Authorities auth = new Authorities();
		auth.setUsername(username);
		auth.setAuthority("ROLE_NEW");
		
		userService.userActivation(users);
		userService.setAuthority(auth);
		
		List<User> updateduser = userService.getAllNewUsers();
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
		
	}

	@RequestMapping("/viewedituserdetails")
	public String viewEditUserDetails(Model model) {
		List<User> userlist = userService.getAllActiveUsers();
		model.addAttribute("user", userlist);
		return "viewedituserdetails";
	}
	
	@RequestMapping("/editbtn")
	public String editButton(Model model, @RequestParam("View/Edit") String username) {
		System.out.println("Edit Button Operation");
		User user = userService.getUserregisterbyUsername(username);
		System.out.println(user);
		model.addAttribute("user", user);
		return "edituser";
	}

	@RequestMapping(value = "/updatebtn", method = RequestMethod.POST)
	public String UpdaterUser(@Valid User user, BindingResult result, Model model) {
//		System.out.println(result.getErrorCount());
//		System.out.println(result.toString());
//		System.out.println(user);
		user.setCanlogin(true);
		
		if (result.getErrorCount() > 2)
			return "edituser";
		else {
			userService.createUser(user);	
			List<User> updateduser = userService.getAllActiveUsers();
			model.addAttribute("user", updateduser);
			return "viewedituserdetails";
		}
	}
}
