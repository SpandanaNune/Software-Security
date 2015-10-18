package sbs.web.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
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
		String token = request
                .getParameter("token");
		if (token == null || "".equals(token))
		{
			return "login";
		}
		//Users user = userService.getUserbyField("username", token);
		Users user = userService.getUserbyUsername("arpit");
		//User user = userService.getUserregisterbyUsername("kardanitin");
		model.addAttribute("users", user);
		System.out.println(user);
		return "resetpassword";
	}

	@RequestMapping("/forgotpass")
	public String showForgotPass(Model model, HttpServletRequest request) {
		return "forgotpass";
	}	
	
	@RequestMapping("/forgotPassEmailSuccess")
	public String forgotPassEmailSuccess( HttpServletRequest request) throws IOException
	{
		String email = request
                .getParameter("email");
		String gCaptchaResponse = request
                .getParameter("g-recaptcha-response");
		//Users user = userService.getUserbyField("username", email);
		
		boolean verify = false;
		verify = VerifyCaptcha.verify(gCaptchaResponse);
        if (verify){
        	User user = userService.getUserregisterbyUsername(email);
    		System.out.println(user);
    		if (user != null){
    			SecureRandom random = new SecureRandom();
    			String token = new BigInteger(130, random).toString(32);
    			String url = request.getScheme() + "://"+ request.getServerName() + request.getContextPath() + "/resetpassword?token=" + token;
    			SendMail mail = new SendMail();
    			mail.resetPasswordLink("Nitin","kardanitin@gmail.com", url);
    			return "forgotPassEmailSuccess";
    		}
        }
        return "forgotpass";
	}
	@RequestMapping(value = "/resetpasswordbtn", method = RequestMethod.POST)
	public String resetPassword(Model model, @Valid Users users, BindingResult result, HttpServletRequest request) throws IOException {
		Users user = userService.getUserbyUsername(users.getUsername());
		System.out.println(user);
		boolean verify = false;
        if (verify){
        	return "homepage";
        }
        else{
        	return null;
        }
	}

	@RequestMapping("/systemadmin")
	public String showAdminHome() {
		return "adminhome";
	}
	
/*************************************SYSTEM**MANAGER*******************************/
	
	@RequestMapping("/manager")
	public String showManagerHome(Model model) {
//		List<User> user = userService.getAllNewUsers();
//		model.addAttribute("user", user);
		return "managerhome";
	}
	
	@RequestMapping("/usersignuprequest")
	public String showUserSignUpRequest(Model model) {
		List<User> user = userService.getAllNewUsers();
		model.addAttribute("user", user);
		return "usersignuprequest";
	}
	
	@RequestMapping("/declinebtn")
	public String deleteUserSignUp(Model model, @RequestParam("Decline") String username) {
		System.out.println("Delete Button Operation");
		System.out.println(username);
		
		userService.deleteUserRequest(username);
		List<User> updateduser = userService.getAllNewUsers();
		
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
		
	}	
	@RequestMapping("/acceptbtn")
	public String acceptUserSignUp(Model model, @RequestParam("Accept") String username) {
	 
		User user = userService.getUserregisterbyUsername(username);
		user.setCanlogin(true);
		user.setIsnewuser(false);
		userService.createUser(user);
		System.out.println("hello"+user);

		Users users = new Users();
		users.setUsername(username);
		users.setPassword("456780");
		users.setEnabled(true);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setCredentialsNonExpired(true);

		Authorities auth = new Authorities();
		auth.setUsername(username);
		auth.setAuthority("ROLE_NEW");
		
		userService.userActivation(users);
		userService.setAuthority(auth);
		
		List<User> updateduser = userService.getAllNewUsers();
		model.addAttribute("user", updateduser);
		return "usersignuprequest";
		
	}

	@RequestMapping("/viewedituserdetails")
	public String viewEditUserDetails(Model model) {
		List<User> user = userService.getAllActiveUsers();
		model.addAttribute("user", user);
		return "viewedituserdetails";
	}
	
	@RequestMapping("/editbtn")
	public String editButton(Model model, @RequestParam("View/Edit") String username) {
		System.out.println("Edit Button Operation");
		User user = userService.getUserregisterbyUsername(username);
		System.out.println(user);
		model.addAttribute("user", user);
		return "edituser";
	}

	@RequestMapping(value = "/updatebtn", method = RequestMethod.POST)
	public String UpdaterUser(@Valid User user, BindingResult result, Model model) {
//		System.out.println(result.getErrorCount());
//		System.out.println(result.toString());
//		System.out.println(user);
		user.setCanlogin(true);
		
		if (result.getErrorCount() > 2)
			return "edituser";
		else {
			userService.createUser(user);	
			List<User> updateduser = userService.getAllActiveUsers();
			model.addAttribute("user", updateduser);
			return "viewedituserdetails";
		}
	}
}
