package sbs.web.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sbs.web.models.Accounts;
import sbs.web.models.PII;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.AccountsService;
import sbs.web.service.UserService;
import sbs.web.utils.PKIUtil;

@Controller
public class ExternalUserController {
	private UserService userService;
	private AccountsService accountService;
	
	private static final Logger logger = Logger.getLogger(ExternalUserController.class);

	private static String defaultPath = System.getProperty("catalina.home") + "/users_keys/";

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setAccountService(AccountsService accountService) {
		this.accountService = accountService;
	}

	
	@RequestMapping(value = "/editmerchantprofile")
	public String viewEditMerchantProfile(Model model, Principal principal) {
		User user = userService.getUserregisterbyUsername(principal.getName());
		model.addAttribute("user", user);
		return "editmerchantprofile";
	}
	
	@RequestMapping(value = "/accountsummary")
	public String showWelcome(Model model, Principal principal) {
		String uname = principal.getName();
		List<Accounts> accountDetails = accountService.getAccountDetails(uname);
		model.addAttribute("accountdetails", accountDetails);
		return "accountsummary";
	}

	
	@RequestMapping(value = "/edituserprofile")
	public String viewEditCustomerProfile(Model model, Principal principal) {
		User user = userService.getUserregisterbyUsername(principal.getName());
		model.addAttribute("user", user);
		return "edituserprofile";
	}

	
	@RequestMapping(value = "/editmerchantprofiledone", method = RequestMethod.POST)
	public String viewEditMerchantProfileDone(HttpServletRequest request, @Valid User user, BindingResult result,
			Model model, Principal principal) {
		if (result.hasErrors())
			return "editmerchantprofile";
		boolean canEdit = principal.getName().equals(user.getUsername());
		if (canEdit){			
		
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
				try {
					Part filepart = request.getPart("file");
					FilenameUtils util = new FilenameUtils();
					String priKeyPath = util.separatorsToSystem(defaultPath + user.getUsername() + "/private.key");
					final Path destination = Paths.get(priKeyPath);
					Files.copy(filepart.getInputStream(), destination);
					boolean resultPKI = PKIUtil.validateKeyPairs(user, user.getSSN());
					logger.error("resultPKI is :" + resultPKI);
					if (!resultPKI) {
						model.addAttribute("PKIMessage", "Please upload your Private Key");
						return "editmerchantprofile";
					}
				} catch (Exception e) {
					logger.error("Failure :" + e.getMessage());
					logger.error("Failed to validate PKI");
				}
				
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
		}
		else{
			return "accessdenied";
		}
		return "welcome";

	}

	@RequestMapping(value = "/edituserprofiledone", method = RequestMethod.POST)
	public String viewEditCustomerProfileDone(HttpServletRequest request, @Valid User user, BindingResult result,
			Model model, Principal principal) {
		if (result.hasErrors())
			return "edituserprofile";
		boolean canEdit = principal.getName().equals(user.getUsername());
		if (canEdit){			
		
			User dbUser = userService.getUserregisterbyUsername(user.getUsername());
			if (dbUser.getSSN().equalsIgnoreCase(user.getSSN())) {
				user.setEmail(dbUser.getEmail());
				user.setDob(dbUser.getDob());
				userService.createUser(user);
			} else {
	
				// PKI related
				try {
					Part filepart = request.getPart("file");
					FilenameUtils util = new FilenameUtils();
					String priKeyPath = util.separatorsToSystem(defaultPath + user.getUsername() + "/private.key");
					final Path destination = Paths.get(priKeyPath);
					Files.copy(filepart.getInputStream(), destination);
					boolean resultPKI = PKIUtil.validateKeyPairs(user, user.getSSN());
					logger.error("resultPKI is :" + resultPKI);
					if (!resultPKI) {
						model.addAttribute("PKIMessage", "Please upload your Private Key");
						return "edituserprofile";
					}
				} catch (Exception e) {
					logger.error("Failed to validate PKI");
					logger.error("Failure :" + e.getMessage());
				}
				
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
		}
		else{
			return "accessdenied";
		}
		// **************** check if this is correct
		return "welcome";
	}
	
	
//	@RequestMapping("/editmerchant")
//	public String editMerchantDetails(Model model, Principal principal) {
//		Users user = userService.getUserbyUsername(principal.getName());
//		model.addAttribute("users", user);
//		System.out.println(user);
//		return "merchanthome";
//	}
	

//	@RequestMapping(value = "/updatemerchantbtn", method = RequestMethod.POST)
//	public String UpdaterMerchantUser(@Valid User user, BindingResult result, Model model, Principal principal) {
//		// System.out.println(result.getErrorCount());
//		// System.out.println(result.toString());
//		// System.out.println(user);
//		// user.setCanlogin(true);
//
//		if (result.hasErrors())
//			return "editmerchant";
//		else {
//			boolean canEdit = principal.getName().equals(user.getUsername());
//			if (canEdit){			
//				userService.createUser(user);
//				List<User> updateduser = userService.getAllActiveUsers();
//				model.addAttribute("user", updateduser);
//				return "viewedituserdetails";
//			}
//		}
//		return "accessdenied";
//	}
//

}