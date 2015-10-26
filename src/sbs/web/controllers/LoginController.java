package sbs.web.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
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
import sbs.web.service.AccountsService;
import sbs.web.service.UserService;
import sbs.web.utilities.SendMail;
import sbs.web.utilities.VerifyCaptcha;

@Controller
public class LoginController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	private UserService userService;
	private static int bankerIndex = 0;

	private AccountsService accountService;


	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setAccountService(AccountsService accountService) {
		this.accountService = accountService;
	}
	

	@RequestMapping("/login")
	public String showLogin( HttpServletRequest request,Principal principal,Model model) {

		logger.info("In the login controller");
		return "login";
	}

	@RequestMapping(value="/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}

	@RequestMapping("/resetpassword")
	public String showForgotPassword(Model model, HttpServletRequest request) {

		String token = request.getParameter("token");
		if (token == null || "".equals(token)) {
			return "login";
		}
		User user = userService.getUserProfilebyField("reset_pass_token", token);
		if (user != null) {
			model.addAttribute("users", user);
			return "resetpassword";
		}
		return "login";
	}

	@RequestMapping(value = "/resetpasswordbtn", method = RequestMethod.POST)
	public String resetPassword(Model model, @Valid User user, BindingResult result,
			@RequestParam("password") String password) throws IOException {
		List<Users> userlist = userService.getUsersByField("username", user.getUsername());

		Users users = userlist.get(0);

		users.setPassword(password);
		userService.saveOrUpdateUsers(users);
		User user_profile = userService.getUserProfilebyField("username", user.getUsername());
		user_profile.setReset_pass_token(null);
		userService.updateUser(user);
		return "homepage";
	}

	@RequestMapping("/forgotpass")
	public String showForgotPass(Model model, HttpServletRequest request) {
		return "forgotpass";
	}

	@RequestMapping("/forgotPassEmailSuccess")
	public String forgotPassEmailSuccess(Model model, HttpServletRequest request) throws IOException {
		String email = request.getParameter("email");
		String gCaptchaResponse = request.getParameter("g-recaptcha-response");

		boolean verify = false;
		verify = VerifyCaptcha.verify(gCaptchaResponse);
		if (verify) {
			User user = userService.getUserProfilebyField("email", email);
			if (user != null) {
				SecureRandom random = new SecureRandom();
				String token = new BigInteger(130, random).toString(32);
				String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath()
						+ "/resetpassword?token=" + token;
				user.setReset_pass_token(token);
				userService.updateUser(user);
				SendMail mail = new SendMail();
				mail.resetPasswordLink(user, url);
				return "forgotPassEmailSuccess";
			} else {
				return "forgotpass";
			}
		}
		return "forgotpass";
	}

	@RequestMapping("/systemadmin")
	public String showAdminHome() {
		return "adminhome";
	}
	
	@RequestMapping("/accountactivation")
	public String activateAccount(@Valid Users eUser, BindingResult result, Model model) {
		if (eUser != null && eUser.getUsername() != null) {
			User user = userService.getUserregisterbyUsername(eUser.getUsername());
			// user.setCanlogin(true);
			user.setIsnewuser(false);
			userService.createUser(user);
			System.out.println("hello" + user);
			Users users = new Users();
			users.setUsername(eUser.getUsername());
			users.setPassword(eUser.getPassword());
			users.setEnabled(true);
			users.setAccountNonExpired(true);
			users.setAccountNonLocked(true);
			users.setCredentialsNonExpired(true);

			Authorities auth = new Authorities();
			auth.setUsername(eUser.getUsername());
			// auth.setAuthority(user.getRole());

			userService.saveOrUpdateUsers(users);
			userService.setAuthority(auth);
		} else {
			model.addAttribute("users", new Users());
		}
		return "accountactivation";
	}

}