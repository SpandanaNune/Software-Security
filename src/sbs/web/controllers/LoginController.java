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

import sbs.web.models.UserProfile;
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
		List<UserProfile> user = userService.getAllNewUsers();
		model.addAttribute("user", user);
		return "usersignuprequest";
	}
	@RequestMapping("/viewedituserdetails")
	public String viewEditUserDetails(Model model) {
		List<UserProfile> user = userService.getAllNewUsers();
		model.addAttribute("user", user);
		return "viewedituserdetails";
	}

	@RequestMapping("/deletebtn")
	public String deleteUserSignUp(Model model, @RequestParam("Edit") String username) {
		// List <User> user = userService.getAllUsers();
		// model.addAttribute("user",user);
		System.out.println(username);
		userService.deleteUserRequest(username);
		System.out.println("Delete Button Operation");
		List<UserProfile> updateduser = userService.getAllNewUsers();
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
		
	}
	
	@RequestMapping("/acceptbtn")
	public String acceptUserSignUp(Model model, @RequestParam("Accept") String username) {
		// List <User> user = userService.getAllUsers();
		// model.addAttribute("user",user);
		System.out.println(username);
		Users user = new Users();
		user.setUsername(username);
		user.setPassword("45678");
		userService.userActivation(user);
		//		userService.deleteUserRequest(username);
		System.out.println("Delete Button Operation");
		List<UserProfile> updateduser = userService.getAllNewUsers();
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
		
	}
	
	@RequestMapping("/editbtn")
	public String editButton(Model model, @RequestParam("View/Edit") String username) {
		System.out.println("Edit Button Operation");
		UserProfile user = userService.getUserregisterbyUsername(username);
		model.addAttribute("user", user);
		System.out.println(user);
		return "edituser";
	}

	@RequestMapping(value = "/updatebtn", method = RequestMethod.POST)
	public String UpdaterUser(@Valid UserProfile user, BindingResult result, Model model) {
		System.out.println(result.getErrorCount());
		System.out.println(result.toString());
		
		if (result.getErrorCount() > 2)
			return "edituser";
		else {
			List<UserProfile> updateduser = userService.getAllUsers();
			model.addAttribute("user", updateduser);
			return "usersignuprequest";
		}
	}

}
