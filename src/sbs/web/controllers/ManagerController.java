package sbs.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sbs.web.models.Accounts;
import sbs.web.models.Authorities;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;
import sbs.web.utilities.SendMail;

@Controller
public class ManagerController {
	private static int bankerIndex = 0; 
	private static final Logger logger = Logger.getLogger(ManagerController.class);
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


//	@RequestMapping("/manager")
//	public String showManagerHome(Model model) {
//		// List<User> user = userService.getAllNewUsers();
//		// model.addAttribute("user", user);
//		return "managerhome";
//	}
	
	@RequestMapping("/usersignuprequest")
	public String showUserSignUpRequest(Model model) {
		logger.info("Listing Signup Requests for new users");
		try{
			//List<User> user = userService.getAllNewUsers();
			List<User> user_list = userService.getAllNewRoleUsers();
			List<User> user = new ArrayList<User>();
			user.addAll(user_list);
			model.addAttribute("user", user);
		}
		catch (Exception e){
			logger.error("Error Fetching new users from the db");
		}
		return "usersignuprequest";
	}
	
	@RequestMapping("/declinebtn")
	public String declineUserSignUp(Model model, @RequestParam("Decline") String username) {
		User user = userService.getUserProfileByField("username", username).get(0);
		user.setIsnewuser(false);
		user.setIs_deleted(true);
		userService.createUser(user);

		//List<User> updateduser = userService.getAllNewUsers();
		List<User> user_list = userService.getAllNewRoleUsers();
		List<User> updateduser = new ArrayList<User>(); 
		updateduser.addAll(user_list);
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
	}

	@RequestMapping("/acceptbtn")
	public String acceptUserSignUp(Model model, @RequestParam("Accept") String username) {
		long account1 = 0, account2 = 0;

		User user = userService.getUserProfileByField("username", username).get(0);
		// User user = userService.getUserregisterbyUsername(username);
		// user.setCanlogin(true);
		user.setIsnewuser(false);
		String email = user.getEmail();
		userService.createUser(user);
		// System.out.println("hello" + user);

		Users users = new Users();
		users.setUsername(username);
		String tempPassword = UtilityController.generatePassword();

		SendMail sendmail = new SendMail();
		sendmail.sendTempPassword(email, tempPassword, user.getFirstname());

		users.setPassword(tempPassword);
		users.setEnabled(true);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setCredentialsNonExpired(true);
		users.setEmail(email);
		users.setSiteKeyID(1);
		users.setQ1(" ");
		users.setQ2(" ");
		users.setQ3(" ");

		userService.saveOrUpdateUsers(users);
		Authorities auth = new Authorities();
		auth.setUsername(username);
		auth.setAuthority("ROLE_NEW");
		userService.setAuthority(auth);

		System.out.println("Creating account Numbers");

		boolean account1IsNotValid = true;
		List<Accounts> accountsList1;
		while (account1IsNotValid) {
			System.out.println("GEtting account 1");
			account1 = UtilityController.generateAccountNumber();
			accountsList1 = userService.getAccountsByField("accountNo", account1);
			if (accountsList1.size() == 0) {
				System.out.println("Got account 1");
				account1IsNotValid = false;
			}
		}
		boolean account2IsNotValid = true;
		List<Accounts> accountsList2;
		while (account2IsNotValid) {
			System.out.println("Getting account 2");
			account2 = UtilityController.generateAccountNumber();
			accountsList2 = userService.getAccountsByField("accountNo", account2);
			if (accountsList2.size() == 0) {
				account2IsNotValid = false;
				System.out.println("Got account 2");
			}

		}
		System.out.println("Successfully got two account number :" + account1 + ", " + account2);

		List<Authorities> authorisedEmployee = userService.getUserAuthoritiesByField("authority", "ROLE_EMPLOYEE");
		String bankername = authorisedEmployee.get(bankerIndex % authorisedEmployee.size()).getUsername();
		bankerIndex++;

		Accounts newAccount1 = new Accounts();
		Accounts newAccount2 = new Accounts();

		newAccount1.setBalance(0);
		newAccount1.setAccount_type(true);
		newAccount1.setAccountNo(account1);
		newAccount1.setUsername(username);

		/**********************************************
		 * Random Banker
		 ***************************************/
		newAccount1.setBankername(bankername);

		newAccount2.setBalance(0);
		newAccount2.setAccount_type(false);
		newAccount2.setAccountNo(account2);
		newAccount2.setUsername(username);
		/**********************************************
		 * Random Banker
		 ***************************************/
		newAccount2.setBankername(bankername);

		System.out.println(newAccount1);
		System.out.println(newAccount2);

		userService.addNewAccount(newAccount1);
		userService.addNewAccount(newAccount2);
		
		List<User> user_list = userService.getAllNewRoleUsers();
		List<User> updateduser = new ArrayList<User>(); 
		updateduser.addAll(user_list);
		
		//List<User> updateduser = userService.getAllNewUsers();
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
	}

	@RequestMapping("/viewedituserdetails")
	public String viewEditUserDetails(Model model) {
		//List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
		List<Users> userlist = userService.getAllExternalUsersByFieldBool("enabled", true);
		List<User> userProfileList = new ArrayList<User>();
		for (Users user : userlist) {
			userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
		}
		// System.out.println(userlist.size());
		// System.out.println(userlist.get(0).getUsername());
		// List<User> userlist = userService.getAllActiveUsers();
		model.addAttribute("user", userProfileList);
		return "viewedituserdetails";
	}

	@RequestMapping("/editbtn")
	public String editUserDetails(Model model, @RequestParam("View/Edit") String username) {
		System.out.println("Edit Button Operation");
		User user = userService.getUserregisterbyUsername(username);
		System.out.println(user);
		model.addAttribute("user", user);
		return "edituser";
	}

	@RequestMapping(value = "/updatebtn", method = RequestMethod.POST)
	public String updateActiveUserDetailsByEmployee(@Valid User user, BindingResult result, Model model) {

		if (result.hasErrors())
			return "edituser";
		else {

			userService.createUser(user);
			//List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
			List<Users> userlist = userService.getAllExternalUsersByFieldBool("enabled", true);
			List<User> userProfileList = new ArrayList<User>();
			for (Users listofuser : userlist) {

				userProfileList
						.add((User) userService.getUserProfileByField("username", listofuser.getUsername()).get(0));
			}
			model.addAttribute("user", userProfileList);
			return "viewedituserdetails";
		}
	}

	@RequestMapping("/deleteactiveusers")
	public String showActiveUsersforDelete(Model model) {
		//List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
		List<Users> userlist = userService.getAllExternalUsersByFieldBool("enabled", true);
		List<User> userProfileList = new ArrayList<User>();
		for (Users user : userlist) {

			userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
		}
		System.out.println(userlist.size());
		System.out.println(userlist.get(0).getUsername());
		// List<User> userlist = userService.getAllActiveUsers();
		model.addAttribute("user", userProfileList);
		return "deleteactiveusers";
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public String deleteActiveUser(Model model, @RequestParam("Delete") String username, Principal principal) {
		
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);
		
		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(), edited_user_auth.getAuthority());
		if (canDelete)
		{
			Users users = userService.getUsersByField("username", username).get(0);
		
			users.setEnabled(false);
			userService.saveOrUpdateUsers(users);

			//List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
			List<Users> userlist = userService.getAllExternalUsersByFieldBool("enabled", true);
			List<User> userProfileList = new ArrayList<User>();
			for (Users user : userlist) {
				userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
			}
			//List<User> userlist = userService.getAllActiveUsers();
			model.addAttribute("user", userProfileList);
			return "deleteactiveusers";
		}
		//TODO Add a page for error showing user cannot be deleted.
		return "error";
	}

	@RequestMapping("/editmanagerprofile")
	public String editManagerProfile(Model model, Principal principal) {
		String uname = principal.getName();
		User user = userService.getUserregisterbyUsername(uname);
		model.addAttribute("user", user);
		return "editmanagerprofile";
	}

	@RequestMapping("/editmanagerprofiledone")
	public String editManagerProfileDone(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors())
			return "editmanagerprofile";
		else {
			userService.createUser(user);
			return "welcome";
		}
	}
	
	private boolean check_if_authorised_to_delete(String myRole, String userRole)
	{
		if ("ROLE_MANAGER".equals(myRole))
		{
			if ("ROLE_MANAGER".equals(userRole) || "ROLE_EMPLOYEE".equals(userRole) || "ROLE_ADMIN".equals(userRole))
				return false;
			else
				return true;
		}
		return false;
	}
}
