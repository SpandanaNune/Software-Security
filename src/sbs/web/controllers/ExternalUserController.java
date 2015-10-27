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
//        List<User> user = userService.getAllNewUsers();
//        model.addAttribute("user", user);
        return "merchanthome";
    }

  
  @RequestMapping("/editmerchant")
  public String editMerchantDetails(Model model,Principal principal) {
	  Users user = userService.getUserbyUsername(principal.getName());
		model.addAttribute("users", user);
		System.out.println(user);
		return "merchanthome";
  }
  
@RequestMapping(value = "/updatemerchantbtn", method = RequestMethod.POST)
public String UpdaterMerchantUser(@Valid User user, BindingResult result, Model model) {
//    System.out.println(result.getErrorCount());
//    System.out.println(result.toString());
//    System.out.println(user);
//    user.setCanlogin(true);
 
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
public String viewEditCustomerProfile(Model model,Principal principal) {
	User user=userService.getUserregisterbyUsername(principal.getName());
	model.addAttribute("user", user);
	return "edituserprofile";
}

@RequestMapping(value = "/editmerchantprofile")
public String viewEditMerchantProfile(Model model,Principal principal) {
	User user=userService.getUserregisterbyUsername(principal.getName());
	model.addAttribute("user", user);
	return "editmerchantprofile";
}


@RequestMapping("/merchantsignuprequest")
public String showMerchantSignUpRequest(Model model) {
	List<User> user = userService.getAllNewMerchants();
	model.addAttribute("user", user);
	return "merchantsignuprequest";
}

@RequestMapping("/declinemerchantbtn")
public String declineMerchantSignUp(Model model, @RequestParam("DeclineMerchant") String username) {

	User user = userService.getUserProfileByField("username", username).get(0);
	user.setIsnewuser(false);
	user.setIs_deleted(true);
	userService.createUser(user);

	List<User> updateduser = userService.getAllNewMerchants();
	model.addAttribute("user", updateduser);
	return "merchantsignuprequest";

}

@RequestMapping("/acceptmerchantbtn")
public String acceptMerchantSignUp(Model model, @RequestParam("AcceptMerchant") String username) {
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
	/*************************************************************
	 * SEND**MAIL
	 **************************************************/
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

	// userService.saveOrUpdateUsers(users);

	Authorities auth = new Authorities();
	auth.setUsername(username);
	auth.setAuthority("ROLE_NEWMERCHANT");
	// userService.setAuthority(auth);

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
	// boolean account2IsNotValid = false;
	// List<Accounts> accountsList2;
	// while (account2IsNotValid) {
	// System.out.println("Getting account 2");
	// account2 = UtilityController.generateAccountNumber();
	// accountsList2 = userService.getAccountsByField("accountNo",
	// account2);
	// if (accountsList2.size() == 0) {
	// account2IsNotValid = false;
	// System.out.println("Got account 2");
	// }
	//
	// }
	System.out.println("Successfully got two account number :" + account1 + ", " + account2);

	Accounts newAccount1 = new Accounts();

	newAccount1.setBalance(0);
	newAccount1.setAccount_type(true);
	newAccount1.setAccountNo(account1);
	newAccount1.setUsername(username);

	/**********************************************
	 * Random Banker
	 ***************************************/
	newAccount1.setBankername("arjun");

	userService.addNewAccount(newAccount1);

	List<User> updateduser = userService.getAllNewMerchants();
	model.addAttribute("user", updateduser);
	return "merchantsignuprequest";
}

@RequestMapping(value = "/editmerchantprofiledone", method = RequestMethod.POST)
public String viewEditMerchantProfileDone(HttpServletRequest request,@Valid User user, BindingResult result,Model model) {
	User dbUser=userService.getUserregisterbyUsername(user.getUsername());
	if(dbUser.getSSN().equalsIgnoreCase(user.getSSN()))
	{
		user.setDob(dbUser.getDob());
		user.setEmail(dbUser.getEmail());
		user.setLastname(dbUser.getLastname());
	userService.createUser(user);
	}
	else
	{
		
//		PKI related
//		try {
//			Part filepart = request.getPart("file");
//			String keyPath = defaultPath + user.getUsername() + "/private.key";
//			final Path destination = Paths.get(keyPath);
//			Files.copy(filepart.getInputStream(), destination);
//			PKIUtil.validateKeyPairs( user,user.getSSN());
//			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
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

	}
	model.addAttribute("user", user);

	return "welcome";
}

@RequestMapping(value = "/edituserprofiledone", method = RequestMethod.POST)
public String viewEditCustomerProfileDone(HttpServletRequest request,@Valid User user, BindingResult result,Model model) {
	User dbUser=userService.getUserregisterbyUsername(user.getUsername());
	if(dbUser.getSSN().equalsIgnoreCase(user.getSSN()))
	{
		user.setEmail(dbUser.getEmail());
		user.setDob(dbUser.getDob());
	userService.createUser(user);
	}
	else
	{
		
//		PKI related
//		try {
//			Part filepart = request.getPart("file");
//			String keyPath = defaultPath + user.getUsername() + "/private.key";
//			final Path destination = Paths.get(keyPath);
//			Files.copy(filepart.getInputStream(), destination);
//			PKIUtil.validateKeyPairs( user,user.getSSN());
//			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
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

	}
	model.addAttribute("user", user);

	return "welcome";
}
	
//@RequestMapping(value = "/registerbtn", method = RequestMethod.POST)
//public String RegisterUser(Model model, @Valid User user, BindingResult result) {
//	System.out.println("Finding errors, " + result.toString());
//	if (result.hasErrors()) {
//		System.out.println("It has errors");
//		return "registeruser";
//	}
//
//	User uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
//	if (uniqueUser == null) {
//		System.out.println(user);
//		user.setIsnewuser(true);
//		// user.setCanlogin(false);
//
//		userService.createUser(user);
//		return "homepage";
//
//	} else {
//		System.out.println("Caught duplicate Username");
//		result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
//		return "registeruser";
//	}
//}
}
