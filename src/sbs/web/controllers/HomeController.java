package sbs.web.controllers;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sbs.web.models.Authorities;
import sbs.web.models.OTP;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;
import sbs.web.service.UtilityService;
import sbs.web.utilities.SendMail;
import sbs.web.utilities.VerifyCaptcha;
import sbs.web.utils.PKIUtil;

@Controller
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
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
		return "home";
	}

	@RequestMapping("/homepage")
	public String showhomepage(Model model) {
		System.out.println("showhome");
		return "homepage";
	}

	@RequestMapping(value = "/welcome")
	public String showWelcome(Model model, Principal principal) {
		String uname = principal.getName();
		model.addAttribute("uname", uname);
		return "welcome";
	}

	// @RequestMapping(value = "/useractivated")
	// public String showCompleteActivation(Model model, Principal principal) {
	// String uname = principal.getName();
	// model.addAttribute("uname", uname);
	// return "useractivated";
	// }

	@RequestMapping(value = "/adminhome")
	public String adminHome(Model model, Principal principal) {
		String uname = principal.getName();
		model.addAttribute("uname", uname);
		return "adminhome";
	}

	@RequestMapping("/customerrorpage")
	public String showCustomError(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth);
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);

		}
		return "customerrorpage";
	}

	@RequestMapping(value = "/registeruser")
	public String showRegisterUser(Model model) {
		model.addAttribute("user", new User());
		return "registeruser";
	}

	@RequestMapping(value = "/merchant")
	public String showMerchantUser(Model model) {
		model.addAttribute("user", new User());
		return "merchant";
	}

	@RequestMapping(value = "/registerbtn", method = RequestMethod.POST)
	public String moveToVerifyOTP(Model model, @Valid User user, BindingResult result, HttpServletRequest request) {
		System.out.println("Finding errors, " + result.toString());
		String gCaptchaResponse = request.getParameter("g-recaptcha-response");
		if (result.hasErrors()) {
			return "registeruser";
		}
		
		List<User> uniqueUser;
		uniqueUser = (userService.getUserProfileByField("username", user.getUsername().toLowerCase()));
		System.out.println("uniqueUser " + uniqueUser);
		if (uniqueUser.size() > 0) {
			System.out.println("Caught duplicate Username");
			result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
			return "registeruser";
		}

		uniqueUser = (userService.getUserProfileByField("email", user.getEmail()));
		if (uniqueUser.size() > 0) {
			System.out.println("Caught duplicate Email");
			result.rejectValue("email", "DuplicateKeyException.user.email", "Email already exists.");
			return "registeruser";
		}
		boolean verify = false;
		try{
			verify = VerifyCaptcha.verify(gCaptchaResponse);
		}catch(Exception e){
			logger.error("Failed to verify captcha");
		}
		
		if (verify) {
			try{
				user.setIsmerchant(false);
				Authorities auth = new Authorities();
				auth.setUsername(user.getUsername());
				auth.setAuthority("ROLE_NEW");
	
				userService.createUser(user);
				userService.setAuthority(auth);
	
				sendOTPMail(user.getFirstname(), user.getEmail());
	
				model.addAttribute("mail", user.getEmail());
				return "completeregistration";
			}
			catch(Exception e){
				logger.error("Failure during user registration::"+e.getMessage());
			}
		}
		else
			return "registeruser";
		return "registeruser";
	}

	@RequestMapping(value = "/merchantregisterbtn", method = RequestMethod.POST)
	public String RegisterMerchant(@Valid User user, BindingResult result, Model model,  HttpServletRequest request) {
		String gCaptchaResponse = request.getParameter("g-recaptcha-response");
		if (result.hasErrors()) {
			return "merchant";
		}

		List<User> uniqueUser;
		uniqueUser = (userService.getUserProfileByField("username", user.getUsername().toLowerCase()));
		if (uniqueUser.size() > 0) {
			result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
			return "merchant";

		}

		uniqueUser = (userService.getUserProfileByField("email", user.getEmail()));
		if (uniqueUser.size() > 0) {
			result.rejectValue("email", "DuplicateKeyException.user.email", "Email already exists.");
			return "merchant";
		}
		boolean verify = false;
		try{
			verify = VerifyCaptcha.verify(gCaptchaResponse);
		}catch(Exception e){
			logger.error("Failed to verify captcha");
		}
		
		if (verify) {
			try{
		
				user.setIsmerchant(true);
				user.setLastname("Merchant");
		
				Authorities auth = new Authorities();
				auth.setUsername(user.getUsername());
				auth.setAuthority("ROLE_NEWMERCHANT");
		
				userService.createUser(user);
				userService.setAuthority(auth);
		
				sendOTPMail(user.getFirstname(), user.getEmail());
		
				model.addAttribute("mail", user.getEmail());
				return "completeregistration";
			}
			catch(Exception e){
				logger.error("Failure during merchant registration::"+e.getMessage());
			}
		}
		else
			return "merchant";
		return "merchant";
	}

	@RequestMapping(value = "/registerbtn2", method = RequestMethod.POST)
	public String RegisterUserComplete(Model model, User user, HttpServletRequest request) {

		String mail = request.getParameter("mail");
		String otpValue = request.getParameter("otpValue");
		System.out.println("mail " + mail);
		User userObj = userService.getUserregisterbyEmail(mail);

		String otpStatus = verifyUserOTP(userObj, otpValue);
		System.out.println("otpStatus " + otpStatus);

		// sending the email back to jsp
		model.addAttribute("mail", mail);

		if (otpStatus.equalsIgnoreCase("success")) {
			userObj.setIsnewuser(true);
			System.out.println(userObj.toString());
			userService.createUser(userObj);
			return "home";
		} else if (otpStatus.equalsIgnoreCase("attempts")) {
			// Too many attempts. Refresh and request OTP again
			System.out.println("Wrong otp, otpStatus: " + otpStatus);
			model.addAttribute("otpstatus", "Incorrect OTP");
			return "completeregistration";
		} else if (otpStatus.equalsIgnoreCase("failure")) {
			// delete userprofile and redirect again to registration.
			System.out.println("Failure refresh and request OTP, otpStatus " + otpStatus);
			userService.deleteUser(userObj);
			model.addAttribute("otpstatus", "Too many attempts, register again");
			return "registeruser";

		}
		// DELETE THIS LATER
		return "home";
	}

	@RequestMapping(value = "/userconfirm")
	public String showUserConfirmation(Model model) {
		model.addAttribute("users", new Users());
		return "userconfirm";
	}

	@RequestMapping(value = "/activateuser", method = RequestMethod.POST)
	public String ActivateUser(@Valid Users users, BindingResult result, Principal principal, HttpServletRequest req,
			HttpServletResponse res, Model model) {
		String uname = principal.getName();
		System.out.println(uname);
		//
		// if (result.hasErrors()) {
		// return "userconfirm";
		// }
		Authorities authority = userService.getUserActivatebyUsername(uname);
		users.setUsername(uname);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setEnabled(true);
		users.setCredentialsNonExpired(true);
		users.setSiteKeyID(1);
		Authorities auth = new Authorities();
		auth.setUsername(uname);
		if ("ROLE_NEWMERCHANT".equals(authority.getAuthority())) {
			auth.setAuthority("ROLE_MERCHANT");
		} else if ("ROLE_NEWMANAGER".equals(authority.getAuthority())) {
			auth.setAuthority("ROLE_MANAGER");
		} else if ("ROLE_NEWEMPLOYEE".equals(authority.getAuthority())) {
			auth.setAuthority("ROLE_EMPLOYEE");
		} else
			auth.setAuthority("ROLE_USER");
		userService.setAuthority(auth);
		userService.saveOrUpdateUsers(users);
		User user_profile = userService.getUserProfilebyField("username", uname);
		PKIUtil pki = new PKIUtil();
		pki.sendPrivateKey(user_profile);
		Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
		if (auth1 != null) {
			new SecurityContextLogoutHandler().logout(req, res, auth1);
			// new PersistentTokenBasedRememberMeServices().logout(request,
			// response, auth);
		}
		return "home";
	}

	public String verifyUserOTP(User user, String otpValue) {
		// String mail=user.getEmail();
		String mail = user.getEmail();
		String firstName = user.getFirstname();

		OTP otpObj = new OTP();
		otpObj.setFirstName(firstName);
		otpObj.setMailID(mail);
		otpObj.setOtpValue(otpValue);
		System.out.println(firstName + " " + mail + " " + otpValue);
		try {
			OTP dbObj = utilityService.checkOTP(otpObj);
			if (dbObj == null) {
				System.out.println("bdObj is null. Go to error page");
				// Go to error page
				return "failure";
			} else {
				System.out.println(
						"DB Object " + dbObj.getFirstName() + " " + dbObj.getMailID() + " " + dbObj.getOtpValue());
				System.out.println("otpObj.getOtpValue() " + otpObj.getOtpValue());
				if (otpObj.getOtpValue().equals(dbObj.getOtpValue())) {
					System.out.println("Correct OTP. Navigate to required page");
					// utilityService.deleteOTP(otpObj);
					return "success";
				} else if (dbObj.getAttempts() == 2) {
					System.out
							.println("Too many attempts. Deleting the OTP. dbObj.getAttempts() " + dbObj.getAttempts());
					userService.deleteUserRequest(user.getUsername());
					utilityService.deleteOTP(otpObj);
					return "failure";
				} else {
					int attempts = dbObj.getAttempts();
					otpObj.setAttempts(attempts + 1);
					utilityService.updateOTP(otpObj);
					return "attempts";
				}
			}
		} catch (Exception e) {
			System.out.println("Printing stack trace");
			e.printStackTrace();
		}
		// return error page
		return null;
	}

	public static String generatePassword() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "1234567890";
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
			System.out.println("otpObj " + otpObj.toString());
			utilityService.insertOTP(otpObj);
			System.out.println("Sending email");
			SendMail sendMail = new SendMail();
			sendMail.sendOTP(otpObj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
