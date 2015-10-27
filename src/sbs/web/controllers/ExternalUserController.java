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

import sbs.web.models.Accounts;
import sbs.web.models.Authorities;
import sbs.web.models.PII;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;

@Controller
public class ExternalUserController {
	private UserService userService;
	private static String defaultPath = System.getProperty("catalina.home") + "/users_keys/";

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/merchanthome")
	public String showMerchantHome(Model model) {
		// List<User> user = userService.getAllNewUsers();
		// model.addAttribute("user", user);
		return "merchanthome";
	}

	// @RequestMapping("/editmerchant")
	// public String editMerchantDetails(Model model) {
	//// List<User> user = userService.getAllNewUsers();
	//// model.addAttribute("usermerchant", user);
	// Users user = userService.getUserbyUsername("arpit");
	// //User user = userService.getUserregisterbyUsername("kardanitin");
	// model.addAttribute("users", user);
	// System.out.println(user);
	// return "merchanthome";
	// }

	@RequestMapping(value = "/updatemerchantbtn", method = RequestMethod.POST)
	public String UpdaterMerchantUser(@Valid User user, BindingResult result, Model model) {
		// System.out.println(result.getErrorCount());
		// System.out.println(result.toString());
		// System.out.println(user);
		// user.setCanlogin(true);

		if (result.getErrorCount() > 2)
			return "editmerchant";
		else {
			userService.createUser(user);
			List<User> updateduser = userService.getAllActiveUsers();
			model.addAttribute("user", updateduser);
			return "viewedituserdetails";
		}
	}

	@RequestMapping(value = "/edituserprofile")
	public String viewEditCustomerProfile(Model model) {
		User user = userService.getUserregisterbyUsername("arjun");
		model.addAttribute("user", user);
		return "edituserprofile";
	}

	@RequestMapping(value = "/editmerchantprofile")
	public String viewEditMerchantProfile(Model model,Principal principal) {	
		User user = userService.getUserregisterbyUsername(principal.getName());
		model.addAttribute("user", user);
		return "editmerchantprofile";
	}

	@RequestMapping(value = "/editmerchantprofiledone", method = RequestMethod.POST)
	public String viewEditMerchantProfileDone(HttpServletRequest request, @Valid User user, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "editmerchantprofile";
		}
		User dbUser = userService.getUserregisterbyUsername(user.getUsername());

		if (dbUser.getSSN() == user.getSSN()) {
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
			userService.createPII(pii);

			user.setSSN(dbUser.getSSN());
			userService.createUser(user);
			result.rejectValue("SSN", "xxxxxxxx", "PII request submitted to admin");

		}
		model.addAttribute("user", user);

		return "editmerchantprofile";
	}

	@RequestMapping(value = "/edituserprofiledone", method = RequestMethod.POST)
	public String viewEditCustomerProfileDone(HttpServletRequest request, @Valid User user, BindingResult result,
			Model model) {
		User dbUser = userService.getUserregisterbyUsername(user.getUsername());
		if (dbUser.getSSN() == user.getSSN()) {
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
			userService.createPII(pii);

			user.setSSN(dbUser.getSSN());
			userService.createUser(user);
			result.rejectValue("SSN", "xxxxxxxx", "PII request submitted to admin");

		}
		model.addAttribute("user", user);

		return "edituserprofile";
	}

	// @RequestMapping(value = "/registerbtn", method = RequestMethod.POST)
	// public String RegisterUser(Model model, @Valid User user, BindingResult
	// result) {
	// System.out.println("Finding errors, " + result.toString());
	// if (result.hasErrors()) {
	// System.out.println("It has errors");
	// return "registeruser";
	// }
	//
	// User uniqueUser =
	// (userService.getUserregisterbyUsername(user.getUsername()));
	// if (uniqueUser == null) {
	// System.out.println(user);
	// user.setIsnewuser(true);
	// // user.setCanlogin(false);
	//
	// userService.createUser(user);
	// return "homepage";
	//
	// } else {
	// System.out.println("Caught duplicate Username");
	// result.rejectValue("username", "DuplicateKeyException.user.username",
	// "Username already exists.");
	// return "registeruser";
	// }
	// }
}
