package sbs.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;

@Controller
public class ExternalUserController {
	private UserService userService;
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
  public String editMerchantDetails(Model model) {
//      List<User> user = userService.getAllNewUsers();
//      model.addAttribute("usermerchant", user);
	  Users user = userService.getUserbyUsername("arpit");
		//User user = userService.getUserregisterbyUsername("kardanitin");
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

@RequestMapping(value = "/merchantregisterbtn", method = RequestMethod.POST)
public String RegisterMerchant(@Valid User user, BindingResult result,Model model) {

	if (result.hasErrors()) {
		return "merchant";
	}

	User uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
	if (uniqueUser == null) {
		System.out.println(user);
			user.setIsnewuser(true);
			user.setIsmerchant(true);
			user.setLastname("Merchant");
		userService.createUser(user);
		return "homepage";
	} else {
		System.out.println("Caught duplicate Username");
		result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
		return "merchant";
	}
}


@RequestMapping(value = "/edituserprofile")
public String viewEditCustomerProfile(Model model) {
	User user=userService.getUserregisterbyUsername("arjun");
	model.addAttribute("user", user);
	return "edituserprofile";
}

@RequestMapping(value = "/edituserprofiledone", method = RequestMethod.POST)
public String viewEditCustomerProfileDone(@Valid User user, BindingResult result,Model model) {
	User dbUser=userService.getUserregisterbyUsername(user.getUsername());
	if(dbUser.getSSN() == user.getSSN())
	{
	userService.createUser(user);
	}
	else
	{
		user.setSSN(dbUser.getSSN());
		userService.createUser(user);
	}
	model.addAttribute("user", user);

	return "edituserprofile";
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
