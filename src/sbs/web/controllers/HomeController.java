package sbs.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sbs.web.dao.User;
import sbs.web.service.UserService;

@Controller
public class HomeController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public String showhome(Model model) {

		// List<User> users = userService.getCurrent();
		// User user = new User(29,"dsndjs", "smadhaxncxcnv2@gmail.com","c");
		// userService.testSave(user);
		
	//	 model.addAttribute("offers", users);
		return "homepage";
	}
	
	@RequestMapping("/viewuser")
	public String showViewUser(Model model) {

		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "viewuser";
	}
	
	@RequestMapping(value="/registeruser")
	public String showRegisterUser(Model model) {	

		model.addAttribute("user", new User());
		return "registeruser";
	}
	@RequestMapping(value="/docreate",method=RequestMethod.POST)
	public String RegisterUser(Model model, User user) {	
		
		userService.createUser(user);
		return "homepage";
	}
	

}

//	@RequestMapping("/one")
//	public String one(Model model) {
//		 List<User> users = userService.getCurrent();
//			// User user = new User(29,"dsndjs", "smadhaxncxcnv2@gmail.com","c");
//			// userService.testSave(user);
//			
//			 model.addAttribute("offers", users);
//		return "one";
//	}
//
//	@RequestMapping("/two")
//	public String two(Model model) {
//
//		return "two";
//	}
//
//	@RequestMapping("/three")
//	public String three(Model model) {
//
//		return "three";
//	}
//
//	@RequestMapping("/captcha")
//	public String captcha(Model model) {
//
//		return "Captcha";
//	}
//   @RequestMapping(value = "/validate")
//   public String validateCaptcha(ModelMap model,
//           HttpServletRequest request)
//   {
//       
//       ReCaptchaImpl captcha = new ReCaptchaImpl();
//       captcha.setPrivateKey("6LeeTusSAAAAAJ-XXXXXXXXXXXXXXXXXXXXXX");
//       
//       String challenge = request.getParameter("recaptcha_challenge_field");
//       String uresponse = request.getParameter("recaptcha_response_field");
//       ReCaptchaResponse reCaptchaResponse =
//               captcha.checkAnswer(request.getRemoteAddr(),
//               challenge, uresponse
//           );
//
//       if (reCaptchaResponse.isValid()) {
//           model.addAttribute("message", "Captcha Validated");
//       } else {
//           model.addAttribute("message", "Captcha Validated failed.");
//       }
//       return "result";
//   }
//}
