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

	@RequestMapping("/usersignuprequest")
	public String showUserSignUpRequest(Model model) {
		logger.info("Listing Signup Requests for new users");
		try {
			// List<User> user = userService.getAllNewUsers();
			List<User> user_list = userService.getAllNewRoleUsers();
			List<User> user = new ArrayList<User>();
			user.addAll(user_list);
			model.addAttribute("user", user);
		} catch (Exception e) {
			logger.error("Error Fetching new users from the db");
			logger.error("Failure :" + e.getMessage());
		}
		return "usersignuprequest";
	}

	@RequestMapping("/merchantsignuprequest")
	public String showMerchantSignUpRequest(Model model) {

		List<User> user_list = userService.getAllNewRoleMerchants();
		List<User> user = new ArrayList<User>();
		user.addAll(user_list);
		model.addAttribute("user", user);

		return "merchantsignuprequest";
	}

	@RequestMapping("/declinebtn")
	public String declineUserSignUp(Model model, @RequestParam("Decline") String username) {
		User user = userService.getUserProfileByField("username", username).get(0);
		user.setIsnewuser(false);
		user.setIs_deleted(true);
		userService.createUser(user);
		// List<User> updateduser = userService.getAllNewUsers();
		List<User> user_list = userService.getAllNewRoleUsers();
		List<User> updateduser = new ArrayList<User>();
		updateduser.addAll(user_list);
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
	}

	@RequestMapping("/declinemerchantbtn")
	public String declineMerchantSignUp(Model model, @RequestParam("DeclineMerchant") String username) {

		User user = userService.getUserProfileByField("username", username).get(0);
		user.setIsnewuser(false);
		user.setIs_deleted(true);
		userService.createUser(user);

		List<User> user_list = userService.getAllNewRoleMerchants();
		List<User> usermerchant = new ArrayList<User>();
		usermerchant.addAll(user_list);
		model.addAttribute("user", usermerchant);

		return "merchantsignuprequest";

	}

	@RequestMapping("/acceptmerchantbtn")
	public String acceptMerchantSignUp(Model model, @RequestParam("AcceptMerchant") String username, Principal principal) {
		long account1 = 0;
		
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canAccept = check_if_authorised_to_approve(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canAccept) {

			User user = userService.getUserProfileByField("username", username).get(0);
			// User user = userService.getUserregisterbyUsername(username);
			// user.setCanlogin(true);
			user.setIsnewuser(false);
			String email = user.getEmail();
			// System.out.println("hello" + user);
	
			Users users = new Users();
			users.setUsername(username);
			String tempPassword = UtilityController.generatePassword();
	
			users.setPassword(tempPassword);
			users.setEnabled(true);
			users.setAccountNonExpired(true);
			users.setAccountNonLocked(true);
			users.setCredentialsNonExpired(true);
			users.setEmail(email);
			users.setSiteKeyID(1);
			users.setQ1("xxxxx");
			users.setQ2("xxxxx");
			users.setQ3("xxxxx");
	
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
			List<Authorities> authorisedEmployee = userService.getUserAuthoritiesByField("authority", "ROLE_MANAGER");
			String bankername = authorisedEmployee.get(bankerIndex % authorisedEmployee.size()).getUsername();
			bankerIndex++;
	
			Accounts newAccount1 = new Accounts();
			newAccount1.setBalance(0);
			newAccount1.setAccount_type(true);
			newAccount1.setAccountNo(account1);
			newAccount1.setUsername(username);
			newAccount1.setBankername(bankername);
	
			userService.createUser(user);
			userService.saveOrUpdateUsers(users);
			userService.addNewAccount(newAccount1);
	
			SendMail sendmail = new SendMail();
			sendmail.sendTempPassword(email, tempPassword, user.getFirstname());
			
			List<User> user_list = userService.getAllNewRoleMerchants();
			List<User> usermerchant = new ArrayList<User>();
			usermerchant.addAll(user_list);
			model.addAttribute("user", usermerchant);
	
			return "merchantsignuprequest";
		}
		return "accessdenied";
	}

	@RequestMapping("/acceptbtn")
	public String acceptUserSignUp(Model model, @RequestParam("Accept") String username, Principal principal) {
		long account1 = 0, account2 = 0;
		
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canAccept = check_if_authorised_to_approve(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canAccept) {

			User user = userService.getUserProfileByField("username", username).get(0);
			// User user = userService.getUserregisterbyUsername(username);
			// user.setCanlogin(true);
			user.setIsnewuser(false);
			String email = user.getEmail();
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
			users.setQ1("xxxxx");
			users.setQ2("xxxxx");
			users.setQ3("xxxxx");
	
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
	
			try{
				userService.createUser(user);
				userService.saveOrUpdateUsers(users);
				userService.addNewAccount(newAccount1);
				userService.addNewAccount(newAccount2);
			}catch(Exception e){
				logger.error("Failure :" + e.getMessage());
			}
			
	
			List<User> user_list = userService.getAllNewRoleUsers();
			List<User> updateduser = new ArrayList<User>();
			updateduser.addAll(user_list);
	
			// List<User> updateduser = userService.getAllNewUsers();
			model.addAttribute("user", updateduser);
	
			return "usersignuprequest";
		}
		return "accessdenied";
	}

	@RequestMapping("/viewedituserdetails")
	public String viewEditUserDetails(Model model) {
		// List<Users> userlist = userService.getUsersByFieldBool("enabled",
		// true);
		List<Users> userlist = userService.getAllExternalCustomersByFieldBool("enabled", true);
		// List<Users> userlist =
		// userService.getAllExternalUsersByFieldBool("enabled", true);
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

	@RequestMapping("/deleteactiveusers")
	public String showActiveUsersforDelete(Model model) {
		// List<Users> userlist = userService.getUsersByFieldBool("enabled",
		// true);
		List<Users> userlist = userService.getAllExternalMerchantsByFieldBool("enabled", true);

		// List<Users> userlist =
		// userService.getAllExternalUsersByFieldBool("enabled", true);
		List<User> userProfileList = new ArrayList<User>();
		for (Users user : userlist) {

			userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
		}

		// List<User> userlist = userService.getAllActiveUsers();
		model.addAttribute("user", userProfileList);
		
		return "deleteactiveusers";
	}

	@RequestMapping("/editbtn")
	public String editUserDetails(Model model, @RequestParam("View/Edit") String username, Principal principal) {
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {
			System.out.println("Edit Button Operation");
			User user = userService.getUserregisterbyUsername(username);
			System.out.println(user);
			model.addAttribute("user", user);
			return "edituser";
		}
		return "accessdenied";
		
	}

	@RequestMapping(value = "/updatebtn", method = RequestMethod.POST)
	public String updateActiveUserDetailsByEmployee(@Valid User user, BindingResult result, Model model, Principal principal) {

		if (result.hasErrors())
			return "edituser";
		else {
			Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
			Authorities edited_user_auth = userService.getAuthorityByField("username", user.getUsername());

			boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
					edited_user_auth.getAuthority());
			if (canDelete) {

				User dbUser = userService.getUserregisterbyUsername(user.getUsername());
	
				user.setSSN(dbUser.getSSN());
				user.setEmail(dbUser.getEmail());
				user.setDob(dbUser.getDob());
				userService.createUser(user);
				// List<Users> userlist = userService.getUsersByFieldBool("enabled",
				// true);
				List<Users> userlist = userService.getAllExternalCustomersByFieldBool("enabled", true);
				List<User> userProfileList = new ArrayList<User>();
				for (Users listofuser : userlist) {
	
					userProfileList
							.add((User) userService.getUserProfileByField("username", listofuser.getUsername()).get(0));
				}
				model.addAttribute("user", userProfileList);
				return "viewedituserdetails";
			}
		}
		return "accessdenied";
		
	}

	@RequestMapping("/editbtnmerchant")
	public String editMerchantDetails(Model model, @RequestParam("View/Edit") String username, Principal principal) {
		System.out.println("Edit Button Operation");
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {
			User user = userService.getUserregisterbyUsername(username);
			System.out.println(user);
			model.addAttribute("user", user);
			return "editmerchant";
		}
		return "accessdenied";
	}

	@RequestMapping(value = "/updatebtnmerchant", method = RequestMethod.POST)
	public String updateActiveMerchantDetailsByEmployee(@Valid User user, BindingResult result, Model model, Principal principal) {

		if (result.hasErrors())
			return "editmerchant";
		else {
			Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
			Authorities edited_user_auth = userService.getAuthorityByField("username", user.getUsername());

			boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
					edited_user_auth.getAuthority());
			if (canDelete) {

				User dbUser = userService.getUserregisterbyUsername(user.getUsername());
	
				user.setLastname(dbUser.getLastname());
				user.setSSN(dbUser.getSSN());
				user.setEmail(dbUser.getEmail());
				user.setDob(dbUser.getDob());
				userService.createUser(user);
				// List<Users> userlist = userService.getUsersByFieldBool("enabled",
				// true);
				List<Users> userlist = userService.getAllExternalMerchantsByFieldBool("enabled", true);
				List<User> userProfileList = new ArrayList<User>();
				for (Users listofuser : userlist) {
	
					userProfileList
							.add((User) userService.getUserProfileByField("username", listofuser.getUsername()).get(0));
				}
				model.addAttribute("user", userProfileList);
				return "deleteactiveusers";
			}
		}
		return "accessdenied";
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
	public String deleteActiveUser(Model model, @RequestParam("Delete") String username, Principal principal) {

		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {
			Users users = userService.getUsersByField("username", username).get(0);
			User user_profile = userService.getUserProfilebyField("username",username);

			users.setEnabled(false);
			userService.saveOrUpdateUsers(users);
			SendMail sendmail = new SendMail();
			sendmail.sendBlockAccount(user_profile.getEmail(),users.getUsername());

			// List<Users> userlist = userService.getUsersByFieldBool("enabled",
			// true);
			List<Users> userlist = userService.getAllExternalUsersByFieldBool("enabled", true);
			List<User> userProfileList = new ArrayList<User>();
			for (Users user : userlist) {
				userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
			}
			// List<User> userlist = userService.getAllActiveUsers();
			model.addAttribute("user", userProfileList);
			return "viewedituserdetails";
		}
		// TODO Add a page for error showing user cannot be deleted.
		return "accessdenied";
	}

	@RequestMapping(value = "/deletemerchant", method = RequestMethod.POST)
	public String deleteActiveMerchants(Model model, @RequestParam("Delete") String username, Principal principal) {

		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {
			Users users = userService.getUsersByField("username", username).get(0);
			User user_profile = userService.getUserProfilebyField("username",username);
			users.setEnabled(false);
			userService.saveOrUpdateUsers(users);
			SendMail sendmail = new SendMail();
			sendmail.sendBlockAccount(user_profile.getEmail(),users.getUsername());

			// List<Users> userlist = userService.getUsersByFieldBool("enabled",
			// true);
			List<Users> userlist = userService.getAllExternalMerchantsByFieldBool("enabled", true);
			List<User> userProfileList = new ArrayList<User>();
			for (Users user : userlist) {
				userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
			}
			// List<User> userlist = userService.getAllActiveUsers();
			model.addAttribute("user", userProfileList);
			return "deleteactiveusers";
		}
		// TODO Add a page for error showing user cannot be deleted.
		return "accessdenied";
	}

	@RequestMapping("/editmanagerprofile")
	public String editManagerProfile(Model model, Principal principal) {
		String uname = principal.getName();
		User user = userService.getUserregisterbyUsername(uname);
		model.addAttribute("uname", uname);
		model.addAttribute("user", user);
		return "editmanagerprofile";
	}

	@RequestMapping("/editmanagerprofiledone")
	public String editManagerProfileDone(@Valid User eUser, BindingResult result, Model model, Principal principal) {
		if (result.hasErrors())
			return "editmanagerprofile";
		else {
			boolean canEdit = principal.getName().equals(eUser.getUsername());
			if (canEdit){
			User user = userService.getUserregisterbyUsername(eUser.getUsername());
			eUser.setSSN(user.getSSN());
			eUser.setEmail(user.getEmail());
			eUser.setDob(user.getDob());
			userService.createUser(eUser);
			return "welcome";
			}
		}
		return "accessdenied";
	}

	private boolean check_if_authorised_to_delete(String myRole, String userRole) {
		if ("ROLE_MANAGER".equals(myRole)) {
			if ("ROLE_MANAGER".equals(userRole) || "ROLE_EMPLOYEE".equals(userRole) || "ROLE_ADMIN".equals(userRole))
				return false;
			else
				return true;
		}
		return false;
	}
	
	private boolean check_if_authorised_to_approve(String myRole, String userRole) {
		if ("ROLE_MANAGER".equals(myRole)) {
			if ("ROLE_ADMIN".equals(userRole) || "ROLE_EMPLOYEE".equals(userRole) || "ROLE_MANAGER".equals(userRole))
				return false;
			else
				return true;
		}
		return false;
	}

}
