package sbs.web.controllers;

import java.security.Principal;

import java.security.SecureRandom;
import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sbs.web.models.Authorities;
import sbs.web.models.OTP;
import sbs.web.models.PII;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;
import sbs.web.service.UtilityService;
import sbs.web.utilities.SendMail;

@Controller
public class HomeController {
	private UserService userService;
	private UtilityService utilityService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setUtilityService(UtilityService utilityService) {
		this.utilityService = utilityService;
	}


	@RequestMapping("/")
	public String showhome(Model model) {
		System.out.println("showhome");
		return "homepage";
	}

	@RequestMapping("/Sample")
	public String showRandom() {
		System.out.println("random page");
		return "Sample";
	}

	@RequestMapping("/viewuser")
	public String showViewUser(Model model) {
		System.out.println("showViewUser");
		List<User> users = userService.getAllUsers();
		model.addAttribute("user", users);
		return "viewuser";
	}

	// @RequestMapping(value = "/mylogin")
	// public String loginUser(Model model) {
	// System.out.println("loginUser");
	// model.addAttribute("userlogin", new User());
	// return "mylogin";
	// }

	// @RequestMapping(value = "/loginbtn")
	// public String loginbtn(Model model, User user) {
	// System.out.println("loginbtn");
	// User validatedUser = userService.validateUser(user);
	//
	// if (validatedUser != null)
	//
	// {
	// String[] splits = validatedUser.getPassword().split(",");
	// String storedHash = splits[0];
	// String salt = splits[1];
	//
	// String hashedPwd = Utilities.hash_SHA(user.getPassword() + salt);
	//
	// System.out.println("validatedUser " + validatedUser);
	// if (hashedPwd.equalsIgnoreCase(storedHash)) {
	// System.out.println("Validated");
	// model.addAttribute("loggeduser", user);
	// return "viewuser";
	// }
	// }
	// model.addAttribute("userlogin", new User());
	// return "mylogin";
	// }

	@RequestMapping(value = "/registeruser")
	public String showRegisterUser(Model model) {
		
		System.out.println("showRegisterUser");
		model.addAttribute("user", new User());
		return "registeruser";
	}

	@RequestMapping(value = "/signup")
	public String showRegister(Model model) {
		model.addAttribute("externaluser", new User());
		return "signup";
	}

	// @RequestMapping(value="/logout")
	// public String logoutUser(Model model) {
	// return "homepage";
	// }

	@RequestMapping(value = "/mylogin")
	public String loginUser(Model model) {
		System.out.println("loginUser");
		model.addAttribute("userlogin", new User());
		return "mylogin";
	}

	
	@RequestMapping(value = "/merchant")
	public String showMerchantUser(Model model) {
		System.out.println("showRegisterMerchant");
		model.addAttribute("user", new User());
		return "merchant";
	}
	
	@RequestMapping(value = "/userconfirm")
	public String showUserConfirmation(Model model) {
		System.out.println("User Confirmation");
		model.addAttribute("users", new Users());
		return "userconfirm";
	}

	// @RequestMapping(value = "/activateuser", method = RequestMethod.POST)
	// public String ActivateUser(@Valid Users users, BindingResult result,
	// Principal principal) {
	// String uname = principal.getName();
	// System.out.println(uname);
	// // if (result.hasErrors()) {
	// // // model.addAttribute("user", user);
	// // return "userconfirmation";
	// // }
	// users.setUsername(uname);
	// users.setAccountNonExpired(true);
	// users.setAccountNonLocked(true);
	// users.setEnabled(true);
	// users.setCredentialsNonExpired(true);
	//
	// Authorities auth = new Authorities();
	// auth.setUsername(uname);
	// auth.setAuthority("ROLE_USER");
	// userService.setAuthority(auth);
	//
	// userService.userActivation(users);
	// return "homepage";
	// }

	@RequestMapping(value = "/welcome")
	public String showWelcome() {
		System.out.println("SHOW WELCOME");
		return "welcome";
	}

	@RequestMapping(value = "/activateuser", method = RequestMethod.POST)
	public String ActivateUser(@Valid Users users, BindingResult result, Principal principal) {
		String uname = principal.getName();
		System.out.println(uname);
//		if (result.hasErrors()) {
//			return "userconfirmation";
//		}
		users.setUsername(uname);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setEnabled(true);
		users.setCredentialsNonExpired(true);
		users.setSiteKeyID(1);

		Authorities auth = new Authorities();
		auth.setUsername(uname);
		auth.setAuthority("ROLE_USER");
		userService.setAuthority(auth);

		userService.saveOrUpdateUsers(users);
		return "homepage";
	}

	@RequestMapping(value = "/adminhome")
	public String adminHome(Model model) {

		System.out.println("Admin Home");
		return "adminhome";
	}

	@RequestMapping(value = "/registerbtn", method = RequestMethod.POST )
	public String moveToVerifyOTP(Model model, @Valid User user, BindingResult result) {
		System.out.println("Finding errors, "+result.toString());
//		if (result.hasErrors()) {
//			System.out.println("It has errors");
//			return "registeruser";
//		}
		//insert otp into database
		user.setIsmerchant(false);
		sendOTPMail(user.getFirstname(), user.getEmail());
		//insert to database
		System.out.println("Inserting USer into database");
		userService.createUser(user);
		model.addAttribute("mail",user.getEmail());
		return "completeregistration";
	}
	
	public static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"1234567890";
        final int PW_LENGTH = 8;
        Random rnd = new SecureRandom();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < PW_LENGTH; i++)
            pass.append(chars.charAt(rnd.nextInt(chars.length())));
        return pass.toString();
    }
	
	void sendOTPMail(String firstName, String mail) {
		
		// generate otp
		String otp = generatePassword();
		
		OTP otpObj = new OTP();
		otpObj.setFirstName(firstName);
		otpObj.setMailID(mail);
		otpObj.setOtpValue(otp);
		// otpObj.setTimeStamp(new Date());
		
		try {
			System.out.println("Sending email. Here");
			System.out.println("otpObj "+ otpObj.toString());
			utilityService.insertOTP(otpObj);
			System.out.println("Sending email");
			SendMail sendMail = new SendMail();
			sendMail.sendOTP(otpObj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	@RequestMapping(value = "/registerbtn2", method = RequestMethod.POST )
	public String RegisterUserComplete(Model model,User user, HttpServletRequest request) {
		//@RequestParam("otpvalue") String otpValue
		//I need Mail ID, first name and OTP
		//I need the whole user object
		//Check OTP- if exists then insert
		
		System.out.println("Final stage");
		String mail=request.getParameter("mail");
		String otpValue=request.getParameter("otpValue");
//		user
		System.out.println("GEtting user object for user name "+mail);
		User userObj=userService.getUserregisterbyEmail(mail);
		
		String otpStatus = verifyUserOTP( userObj, otpValue);
		System.out.println("otpStatus "+otpStatus);
		if(otpStatus.equalsIgnoreCase("success")){

//			 Users uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
//			 if (uniqueUser == null) {
			userObj.setIsnewuser(true);
			System.out.println(userObj.toString());
			userService.createUser(userObj);
			return "homepage";
			// } else {
			// System.out.println("Caught duplicate Username");
			// result.rejectValue("username", "DuplicateKeyException.user.username",
			// "Username already exists.");
			// return "registeruser";
			// }

		}else if(otpStatus.equalsIgnoreCase("attempts")){
			//Too many attempts. Refresh and request OTP again
			System.out.println("Wrong otp, otpStatus "+otpStatus);
		}else if(otpStatus.equalsIgnoreCase("failure")){
			//Wrong OTP
			System.out.println("FAilure refresh and request OTP, otpStatus "+otpStatus);
		}
		//DELETE THIS LATER
		return "homepage";
	}
	
	public String verifyUserOTP(User user, String otpValue) {
		System.out.println("showViewUser");
//		String mail=user.getEmail();
		String mail=user.getEmail();
		String firstName=user.getFirstname();
		
		OTP otpObj = new OTP();
		otpObj.setFirstName(firstName);
		otpObj.setMailID(mail);
		otpObj.setOtpValue(otpValue);
		try{
			OTP dbObj= utilityService.checkOTP(otpObj); 
			if(dbObj==null){
				System.out.println("bdObj is null. Go to error page");
				//Go to error page
				return "failure";
			}
			else{
				System.out.println("DB Object "+dbObj.getFirstName()+" "+dbObj.getMailID()+" "+dbObj.getOtpValue());
				System.out.println("otpObj.getOtpValue() "+otpObj.getOtpValue());
				
				if(otpObj.getOtpValue().equals(dbObj.getOtpValue())){
					System.out.println("Correct OTP. Navigate to required page");
					
//					utilityService.deleteOTP(otpObj);
					return "success";
				}
				else if(dbObj.getAttempts()==2){
					System.out.println("Too many attempts. Deleting the OTP. dbObj.getAttempts() "+dbObj.getAttempts());
					userService.deleteUserRequest(user.getUsername());
					utilityService.deleteOTP(otpObj);
					return "failure";
				}else{
					int attempts=dbObj.getAttempts();
					otpObj.setAttempts(attempts+1);
					utilityService.updateOTP(otpObj);
					return "attempts";
				}
			
			}
				
		}
		
		catch(Exception e){
			System.out.println("Printing stack trace");
			e.printStackTrace();
		}
		return null;
//		User uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
//		if (uniqueUser == null) {
//			System.out.println(user);
//			user.setIsnewuser(true);
//			// user.setCanlogin(false);
//
//			userService.createUser(user);
//			return "homepage";
//
//		} else {
//			System.out.println("Caught duplicate Username");
//			result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
//			return "registeruser";
//		}
	}
}
