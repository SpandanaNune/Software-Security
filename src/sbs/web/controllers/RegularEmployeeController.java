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
import org.springframework.web.bind.annotation.RequestParam;

import sbs.web.models.Accounts;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.AccountsService;
import sbs.web.service.UserService;

@Controller
public class RegularEmployeeController {

	private UserService userService;
	private AccountsService accountService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setAccountService(AccountsService accountService) {
		this.accountService = accountService;
	}

	@RequestMapping(value = "/internalemp")
	public String employeeHome(Model model) {

		System.out.println("Intenal Employee");
		return "internalemp";
	}

	@RequestMapping("/viewedituserdetails_employee")
	public String viewEditUserDetailsByEmployee(Model model,Principal principal) {
		
		ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker(principal.getName());
		List<Users> userlist = new ArrayList<Users>();
		for (Accounts account : accounts) {
			Users user = userService.getUserByFieldBool("enabled", true, account.getUsername());
			if (user != null)
				userlist.add(user);
		}

		List<User> userProfileList = new ArrayList<User>();
		for (Users user : userlist) {
			userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
		}

		model.addAttribute("user", userProfileList);
		return "viewedituserdetails_employee";
	}

	@RequestMapping("/editemployeeprofile")
	public String editEmployeeProfile(Model model, Principal principal) {
		String uname = principal.getName();
		System.out.println("Edit Button Operation");
		User user = userService.getUserregisterbyUsername(uname);
		System.out.println(user);
		model.addAttribute("user", user);
		return "editemployeeprofile";
	}

	@RequestMapping("/editemployeeprofiledone")
	public String editEmployeeProfileDone(@Valid User user, BindingResult result, Model model,Principal principal) {
		System.out.println(user.toString());
		if(result.hasErrors())
			return "editemployeeprofile";
		userService.createUser(user);
		model.addAttribute("uname", principal.getName());
		return "welcome";
	}

	@RequestMapping("/editbtn_employee")
	public String editUserDetailsByEmployee(Model model, @RequestParam("View/Edit") String username) {
		System.out.println("Edit Button Operation");
		User user = userService.getUserregisterbyUsername(username);
		System.out.println(user);
		model.addAttribute("user", user);
		return "edituser_employee";
	}

	@RequestMapping(value = "/updatebtn_employee", method = RequestMethod.POST)
	public String updateActiveUserDetails(@Valid User user, BindingResult result, Model model,Principal principal) {

		if (result.hasErrors())
			return "edituser_employee";
		else {

			userService.createUser(user);
			ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker(principal.getName());
			List<Users> userlist = new ArrayList<Users>();
			for (Accounts account : accounts) {
				Users userNew = userService.getUserByFieldBool("enabled", true, account.getUsername());
				if (userNew != null)
					userlist.add(userNew);
			}

			List<User> userProfileList = new ArrayList<User>();
			for (Users userNew : userlist) {
				userProfileList.add((User) userService.getUserProfileByField("username", userNew.getUsername()).get(0));
			}

			model.addAttribute("user", userProfileList);
			return "viewedituserdetails_employee";
		}
	}
}
