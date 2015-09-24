package sbs.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import sbs.web.dao.User;
import sbs.web.service.UserService;

@Controller
public class HomeController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// @RequestMapping("/")
	// public ModelAndView showhome(HttpSession session){
	// ModelAndView mv = new ModelAndView("home");
	// Map<String, Object> model = mv.getModel();
	// model.put("name", "River");
	// return mv;
	// }
	@RequestMapping("/")
	public String showhome(Model model) {

		// List<User> users = userService.getCurrent();
		// User user = new User(29,"dsndjs", "smadhaxncxcnv2@gmail.com","c");
		// userService.testSave(user);
		
	//	 model.addAttribute("offers", users);
		return "home";
	}

	@RequestMapping("/one")
	public String one(Model model) {
		 List<User> users = userService.getCurrent();
			// User user = new User(29,"dsndjs", "smadhaxncxcnv2@gmail.com","c");
			// userService.testSave(user);
			
			 model.addAttribute("offers", users);
		return "one";
	}

	@RequestMapping("/two")
	public String two(Model model) {

		return "two";
	}

	@RequestMapping("/three")
	public String three(Model model) {

		return "three";
	}

	@RequestMapping("/captcha")
	public String captcha(Model model) {

		return "Captcha";
	}
   @RequestMapping(value = "/validate")
   public String validateCaptcha(ModelMap model,
           HttpServletRequest request)
   {
       
       ReCaptchaImpl captcha = new ReCaptchaImpl();
       captcha.setPrivateKey("6LeeTusSAAAAAJ-XXXXXXXXXXXXXXXXXXXXXX");
       
       String challenge = request.getParameter("recaptcha_challenge_field");
       String uresponse = request.getParameter("recaptcha_response_field");
       ReCaptchaResponse reCaptchaResponse =
               captcha.checkAnswer(request.getRemoteAddr(),
               challenge, uresponse
           );

       if (reCaptchaResponse.isValid()) {
           model.addAttribute("message", "Captcha Validated");
       } else {
           model.addAttribute("message", "Captcha Validated failed.");
       }
       return "result";
   }
}
