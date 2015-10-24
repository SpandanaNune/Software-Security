package sbs.web.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import sbs.web.utilities.VerifyCaptcha;

@Controller
public class LoginController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/login")
	public String showLogin() {
		logger.info("In the login controller");
		return "login";
	}

	@RequestMapping("/logout")
	public String showLoggedOut() {
		System.out.println("login page");
		return "loggedout";
	}

	@RequestMapping("/resetpassword")
	public String showForgotPassword(Model model, HttpServletRequest request) {
		logger.debug("resetpassword page");
		String token = request.getParameter("token");
		if (token == null || "".equals(token)) {
			return "login";
		}
		// Users user = userService.getUserbyField("username", token);
		Users user = userService.getUserbyUsername("arpit");
		// User user = userService.getUserregisterbyUsername("kardanitin");
		model.addAttribute("users", user);
		System.out.println(user);
		return "resetpassword";
	}

	@RequestMapping("/forgotpass")
	public String showForgotPass(Model model, HttpServletRequest request) {
		return "forgotpass";
	}

	@RequestMapping("/forgotPassEmailSuccess")
	public String forgotPassEmailSuccess(HttpServletRequest request) throws IOException {
		String email = request.getParameter("email");
		String gCaptchaResponse = request.getParameter("g-recaptcha-response");
		// Users user = userService.getUserbyField("username", email);

		boolean verify = false;
		verify = VerifyCaptcha.verify(gCaptchaResponse);
		if (verify) {
			User user = userService.getUserregisterbyUsername(email);
			System.out.println(user);
			if (user != null) {
				SecureRandom random = new SecureRandom();
				String token = new BigInteger(130, random).toString(32);
				String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath()
						+ "/resetpassword?token=" + token;
				SendMail mail = new SendMail();
				mail.resetPasswordLink("Nitin", "kardanitin@gmail.com", url);
				return "forgotPassEmailSuccess";
			}
		}
		return "forgotpass";
	}

	@RequestMapping(value = "/resetpasswordbtn", method = RequestMethod.POST)
	public String resetPassword(Model model, @Valid Users users, BindingResult result, HttpServletRequest request)
			throws IOException {
		Users user = userService.getUserbyUsername(users.getUsername());
		System.out.println(user);
		boolean verify = false;
		if (verify) {
			return "homepage";
		} else {
			return null;
		}
	}

	@RequestMapping("/systemadmin")
	public String showAdminHome() {
		return "adminhome";
	}

	/*************************************
	 * SYSTEM**MANAGER
	 *******************************/

	@RequestMapping("/manager")
	public String showManagerHome(Model model) {
		// List<User> user = userService.getAllNewUsers();
		// model.addAttribute("user", user);
		return "managerhome";
	}

	@RequestMapping("/usersignuprequest")
	public String showUserSignUpRequest(Model model) {
		List<User> user = userService.getAllNewUsers();
		model.addAttribute("user", user);
		return "usersignuprequest";
	}

	@RequestMapping("/declinebtn")
	public String declineUserSignUp(Model model, @RequestParam("Decline") String username) {

		User user = userService.getUserProfileByField("username", username).get(0);
		user.setIsnewuser(false);
		user.setIs_deleted(true);
		userService.createUser(user);

		List<User> updateduser = userService.getAllNewUsers();
		model.addAttribute("user", updateduser);
		return "usersignuprequest";

	}

	@RequestMapping("/acceptbtn")
	public String acceptUserSignUp(Model model, @RequestParam("Accept") String username) {
		long account1, account2;

		User user = userService.getUserProfileByField("username", username).get(0);
		// User user = userService.getUserregisterbyUsername(username);
		// user.setCanlogin(true);
		user.setIsnewuser(false);
//		String email = user.getEmail();
		userService.createUser(user);
		// System.out.println("hello" + user);

		Users users = new Users();
		users.setUsername(username);
		String tempPassword = UtilityController.generatePassword();
		/*************************************************************
		 * SEND**MAIL
		 **************************************************/
		users.setPassword(tempPassword);
		users.setEnabled(true);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setCredentialsNonExpired(true);
		// users.setEmail(email);
		users.setSiteKeyID(1);
		users.setQ1(" ");
		users.setQ2(" ");
		users.setQ3(" ");

		account1 = UtilityController.generateAccountNumber();
		account2 = UtilityController.generateAccountNumber();

		// List<Accounts> accountsList1 = new ArrayList<Accounts>();
		// List<Accounts> accountsList2 = new ArrayList<Accounts>();
		// accountsList1 = userService.getAccountsByField("accountNo",
		// account1);
		// accountsList2 = userService.getAccountsByField("accountNo",
		// account2);

		// while(userService.getAccountsByField("accountNo", account1).size() >
		// 0){
		// account1 = UtilityController.generateAccountNumber();
		// }
		// while(userService.getAccountsByField("accountNo", account2).size() >
		// 0){
		// account2 = UtilityController.generateAccountNumber();
		// }

		Accounts newAccount1 = new Accounts();
		Accounts newAccount2 = new Accounts();

		newAccount1.setBalance(0);
		newAccount1.setAccount_type(true);
		newAccount1.setAccountNo(account1);
		newAccount1.setUsername(username);
		/**********************************************
		 * Random Banker
		 ***************************************/
		newAccount1.setBankername("arjun");

		newAccount2.setBalance(0);
		newAccount2.setAccount_type(true);
		newAccount2.setAccountNo(account2);
		newAccount2.setUsername(username);
		/**********************************************
		 * Random Banker
		 ***************************************/
		newAccount2.setBankername("arjun");

		Authorities auth = new Authorities();
		auth.setUsername(username);
		auth.setAuthority("ROLE_NEW");

		userService.userActivation(users);
		userService.setAuthority(auth);
		System.out.println(newAccount1);
		System.out.println(newAccount2);

		userService.addNewAccount(newAccount1);
		userService.addNewAccount(newAccount2);

		List<User> updateduser = userService.getAllNewUsers();
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
	}

	@RequestMapping("/viewedituserdetails")
	public String viewEditUserDetails(Model model) {
		List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
		List<User> userProfileList = new ArrayList<User>();
		for (Users user : userlist) {
			userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
		}
		System.out.println(userlist.size());
		System.out.println(userlist.get(0).getUsername());
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
	public String updateActiveUserDetails(@Valid User user, BindingResult result, Model model) {
		// System.out.println(result.getErrorCount());
		// System.out.println(result.toString());
		// System.out.println(user);
		// user.setCanlogin(true);

		if (result.getErrorCount() > 2)
			return "edituser";
		else {

			userService.createUser(user);
			List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
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
		List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
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
	public String deleteActiveUser(Model model,@RequestParam("Delete") String username) {
		Users users = userService.getUsersByField("username", username).get(0);
		users.setEnabled(false);
		userService.userActivation(users);
		
		List<Users> userlist = userService.getUsersByFieldBool("enabled", true);
		List<User> userProfileList = new ArrayList<User>();
		for (Users user : userlist) {

			userProfileList.add((User) userService.getUserProfileByField("username", user.getUsername()).get(0));
		}
		// List<User> userlist = userService.getAllActiveUsers();
		model.addAttribute("user", userProfileList);
		return "deleteactiveusers";

	}


}
