package sbs.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;
import sbs.web.utils.Utilities;

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
		model.addAttribute("users", new User());
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
	public String showAdminHome(){
		return "adminhome";
	}
	
	@RequestMapping("/manager")
	public String showManagerHome(Model model){
		List <User> user = userService.getAllUsers();
		model.addAttribute("user",user);
		return "managerhome";
	}
	@RequestMapping("/editbtn")
	public String editButton(Model model){
//		List <User> user = userService.getAllUsers();
//		model.addAttribute("user",user);
		System.out.println("Edit Button Operation");
		//System.out.println(useredit);
		return "managerhome";
	}
	@RequestMapping("/deletebtn")
	public String deleteButton(Model model){
//		List <User> user = userService.getAllUsers();
//		model.addAttribute("user",user);
		System.out.println("Delete Button Operation");
		return "managerhome";
	}
	
}
