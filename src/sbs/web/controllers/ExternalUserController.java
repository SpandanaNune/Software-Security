package sbs.web.controllers;

import java.security.Principal;
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

import sbs.web.models.Accounts;
import sbs.web.models.PII;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.AccountsService;
import sbs.web.service.UserService;

@Controller
public class ExternalUserController {
	private UserService userService;
	private AccountsService accountService;

	private static String defaultPath = System.getProperty("catalina.home") + "/users_keys/";

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setAccountService(AccountsService accountService) {
		this.accountService = accountService;
	}

	
	@RequestMapping("/editmerchant")
	public String editMerchantDetails(Model model, Principal principal) {
		Users user = userService.getUserbyUsername(principal.getName());
		model.addAttribute("users", user);
		System.out.println(user);
		return "merchanthome";
	}
	
	@RequestMapping(value = "/accountsummary")
	public String showWelcome(Model model, Principal principal) {
		String uname = principal.getName();
		String accountType = "";
		List<Accounts> accountDetails = accountService.getAccountDetails(uname);
		for(Accounts account:accountDetails){
			if(account.isAccount_type())
				accountType = "CHECKING";
			else
				accountType = "SAVING";
		}
		model.addAttribute("accountdetails", accountDetails);
		
		return "accountsummary";
	}

	@RequestMapping(value = "/updatemerchantbtn", method = RequestMethod.POST)
	public String UpdaterMerchantUser(@Valid User user, BindingResult result, Model model) {
		// System.out.println(result.getErrorCount());
		// System.out.println(result.toString());
		// System.out.println(user);
		// user.setCanlogin(true);

		if (result.hasErrors())
			return "editmerchant";
		else {
			userService.createUser(user);
			List<User> updateduser = userService.getAllActiveUsers();
			model.addAttribute("user", updateduser);
			return "viewedituserdetails";
		}
	}

	@RequestMapping(value = "/edituserprofile")
	public String viewEditCustomerProfile(Model model, Principal principal) {
		User user = userService.getUserregisterbyUsername(principal.getName());
		model.addAttribute("user", user);
		return "edituserprofile";
	}

	@RequestMapping(value = "/editmerchantprofile")
	public String viewEditMerchantProfile(Model model, Principal principal) {
		User user = userService.getUserregisterbyUsername(principal.getName());
		model.addAttribute("user", user);
		return "editmerchantprofile";
	}

	@RequestMapping(value = "/editmerchantprofiledone", method = RequestMethod.POST)
	public String viewEditMerchantProfileDone(HttpServletRequest request, @Valid User user, BindingResult result,
			Model model) {
		if (result.hasErrors())
			return "editmerchantprofile";
		User dbUser = userService.getUserregisterbyUsername(user.getUsername());

		// check has errors

		if (dbUser.getSSN().equalsIgnoreCase(user.getSSN())) {
		
			user.setDob(dbUser.getDob());
			user.setLastname(dbUser.getLastname());
			user.setEmail(dbUser.getEmail());
			user.setLastname(dbUser.getLastname());
			user.setIsmerchant(true);
			userService.createUser(user);
		} else {

			// PKI related
			// try {
			// Part filepart = request.getPart("file");
			// String keyPath = defaultPath + user.getUsername() +
			// "/private.key";
			// final Path destination = Paths.get(keyPath);
			// Files.copy(filepart.getInputStream(), destination);
			// PKIUtil.validateKeyPairs( user,user.getSSN());
			//
			// } catch (Exception e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			PII pii = new PII();
			pii.setUserName(user.getUsername());
			pii.setOldSSN(dbUser.getSSN());
			pii.setNewSSN(user.getSSN());
			pii.setApproved(false);
			pii.setMerchant(true);
			user.setDob(dbUser.getDob());
			user.setEmail(dbUser.getEmail());
			user.setSSN(dbUser.getSSN());
			user.setLastname(dbUser.getLastname());
			userService.createUser(user);
			userService.createPII(pii);
			result.rejectValue("SSN", "xxxxxxxx", "PII request submitted to admin");
			return "editmerchantprofile";
		}
		return "welcome";

	}

	@RequestMapping(value = "/edituserprofiledone", method = RequestMethod.POST)
	public String viewEditCustomerProfileDone(HttpServletRequest request, @Valid User user, BindingResult result,
			Model model) {
		if (result.hasErrors())
			return "edituserprofile";
		User dbUser = userService.getUserregisterbyUsername(user.getUsername());
		if (dbUser.getSSN().equalsIgnoreCase(user.getSSN())) {
			user.setEmail(dbUser.getEmail());
			user.setDob(dbUser.getDob());
			userService.createUser(user);
		} else {

			// PKI related
			// try {
			// Part filepart = request.getPart("file");
			// String keyPath = defaultPath + user.getUsername() +
			// "/private.key";
			// final Path destination = Paths.get(keyPath);
			// Files.copy(filepart.getInputStream(), destination);
			// PKIUtil.validateKeyPairs( user,user.getSSN());
			//
			// } catch (Exception e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			PII pii = new PII();
			pii.setUserName(user.getUsername());
			pii.setOldSSN(dbUser.getSSN());
			pii.setNewSSN(user.getSSN());
			pii.setApproved(false);
			pii.setMerchant(false);
			user.setEmail(dbUser.getEmail());
			user.setDob(dbUser.getDob());
			user.setSSN(dbUser.getSSN());
			userService.createUser(user);
			userService.createPII(pii);
			result.rejectValue("SSN", "xxxxxxxx", "PII request submitted to admin");

			return "edituserprofile";
		}
		// **************** check if this is correct
		return "welcome";
	}
}
