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
import org.springframework.web.bind.annotation.RequestParam;

import sbs.web.models.Authorities;
import sbs.web.models.PII;
import sbs.web.models.User;
import sbs.web.models.Users;
import sbs.web.service.UserService;
import sbs.web.utilities.SendMail;

@Controller
public class AdminController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/getinternalusers")
	public String viewInternalUsers(Model model) {
		List<Authorities> employees = userService.getAllEmployees();
		List<User> userProfileList = new ArrayList<User>();
		List<String> roles = new ArrayList<String>();

		for (Authorities auth : employees) {
			userProfileList.add(userService.getUserregisterbyUsername(auth.getUsername()));
			roles.add(auth.getAuthority());
		}
		model.addAttribute("user", userProfileList);
		model.addAttribute("roles", roles);
		return "vieweditinternalusers";
	}

	@RequestMapping("/editemployee")
	public String editemployee(Model model, @RequestParam("View/Edit") String username) {
		User user = userService.getUserregisterbyUsername(username);
		model.addAttribute("user", user);
		return "employeeUpdation";
	}

	@RequestMapping("/employeeupdationdone")
	public String employeeUpdate(@Valid User eUser, BindingResult result) {
		if(result.hasErrors())
			return "employeeUpdation";
		userService.createUser(eUser);
		return "adminhome";
	}

	@RequestMapping("/deleteemployee")
	public String deleteEmployee(Model model, @RequestParam("Decline") String username) {

		User user = userService.getUserProfileByField("username", username).get(0);
		user.setIsnewuser(false);
		user.setIs_deleted(true);
		userService.createUser(user);

		Authorities employee = userService.getEmployee(username);
		userService.deleteEmployee(employee);

		List<Authorities> employees = userService.getAllEmployees();
		List<User> userProfileList = new ArrayList<User>();
		List<String> roles = new ArrayList<String>();

		for (Authorities auth : employees) {
			userProfileList.add(userService.getUserregisterbyUsername(auth.getUsername()));
			roles.add(auth.getAuthority());
		}
		model.addAttribute("user", userProfileList);
		model.addAttribute("roles", roles);

		return "vieweditinternalusers";

	}

	@RequestMapping(value = "/employeecreation")
	public String createEmployee(HttpServletRequest rqst, @Valid User user, BindingResult result, Model model,Principal principal) {
		if(result.hasErrors())
		{
			List<String> authorities = new ArrayList<>();
			authorities.add("ROLE_NEWEMPLOYEE");
			authorities.add("ROLE_NEWMANAGER");
			model.addAttribute("roles", authorities);
			return "employeecreation";
		}
		if(user != null && user.getUsername() != null) {
			String role = rqst.getParameter("role");
			System.out.println(user);
			User uniqueUser = (userService.getUserregisterbyUsername(user.getUsername()));
			if (uniqueUser == null) {
				System.out.println(user);
//				user.setIsnewuser(true);
				userService.createUser(user);

				
				Users users = new Users();
				users.setUsername(user.getUsername());
				String tempPassword = UtilityController.generatePassword();
				System.out.println(tempPassword);

				SendMail sendmail = new SendMail();
				sendmail.sendTempPassword(user.getEmail(), tempPassword, user.getFirstname());
				
				users.setPassword(tempPassword);
				users.setEnabled(true);
				users.setAccountNonExpired(true);
				users.setAccountNonLocked(true);
				users.setCredentialsNonExpired(true);
				users.setEmail(user.getEmail());
				users.setSiteKeyID(1);
				users.setQ1("xxxxx");
				users.setQ2("xxxxx");
				users.setQ3("xxxxx");
				System.out.println(users);

				userService.saveOrUpdateUsers(users);
				
				Authorities auth = new Authorities();
				auth.setUsername(user.getUsername());
				auth.setAuthority(role);
				userService.setAuthority(auth);
			} else {
				System.out.println("Caught duplicate Username");
				result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
			}
		}
		List<String> authorities = new ArrayList<>();
		authorities.add("ROLE_NEWEMPLOYEE");
		authorities.add("ROLE_NEWMANAGER");
		model.addAttribute("roles", authorities);
		model.addAttribute("uname",principal.getName());
		return "adminhome";
	}

	@RequestMapping(value = "/pii")
	public String listPIIs(Model model) {
		List<PII> piis = userService.getAllPIIs();
		model.addAttribute("piis", piis);
		return "pii";
	}

	@RequestMapping("/acceptpii")
	public String acceptUserPII(Model model, @RequestParam("Accept") String username) {
		User user = userService.getUserregisterbyUsername(username);
		PII pii = userService.getPII(username);
		if (pii.getOldSSN().equalsIgnoreCase(user.getSSN())) {
			user.setSSN(pii.getNewSSN());
			userService.updateUser(user);
			userService.approvePII(pii.getUserName());
		} else {
			// error SSN not matched
		}
		List<PII> piis = userService.getAllPIIs();
		model.addAttribute("piis", piis);
		return "pii";
	}

	@RequestMapping("/declinepii")
	public String declineUserPII(Model model, @RequestParam("Decline") String username) {
		userService.deletePII(username);
		List<PII> piis = userService.getAllPIIs();
		model.addAttribute("piis", piis);
		return "pii";
	}

	@RequestMapping("/editadminprofile")
	public String editManagerProfile(Model model,Principal principal) {
		String uname = principal.getName();
		User user = userService.getUserregisterbyUsername(uname);
		model.addAttribute("user", user);
		return "editadminprofile";
	}

	@RequestMapping("/editadminprofiledone")
	public String editManagerProfileDone(@Valid User user, BindingResult result, Model model) {
		if(result.hasErrors())
			return "editadminprofile";
		userService.createUser(user);
		return "adminhome";
	}

}
