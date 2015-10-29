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
		if (result.hasErrors())
			return "employeeUpdation";

		User user = userService.getUserregisterbyUsername(eUser.getUsername());
		eUser.setSSN(user.getSSN());
		eUser.setEmail(user.getEmail());
		eUser.setDob(user.getDob());
		userService.createUser(eUser);
		return "adminhome";
	}

	@RequestMapping("/deleteemployee")
	public String deleteEmployee(Model model, @RequestParam("Decline") String username) {

		User user = userService.getUserProfileByField("username", username).get(0);
		user.setIsnewuser(false);
		user.setIs_deleted(true);
		userService.createUser(user);
		SendMail sendmail = new SendMail();
		sendmail.sendBlockAccount(user.getEmail(),user.getUsername());

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

	@RequestMapping(value = "/employeecreationdone")
	public String createEmployeeDone(HttpServletRequest rqst, @Valid User user, BindingResult result, Model model,
			Principal principal) {
		// if (user != null && user.getUsername() != null) {
		if (result.hasErrors()) {
			List<String> authorities = new ArrayList<>();
			authorities.add("ROLE_NEWEMPLOYEE");
			authorities.add("ROLE_NEWMANAGER");
			model.addAttribute("roles", authorities);
			return "employeecreation";
		}

		List<User> uniqueUser1;
		uniqueUser1 = (userService.getUserProfileByField("username", user.getUsername().toLowerCase()));
		System.out.println("uniqueUser " + uniqueUser1);
		if (uniqueUser1.size() > 0) {
			System.out.println("Caught duplicate Username");
			List<String> authorities = new ArrayList<>();
			authorities.add("ROLE_NEWEMPLOYEE");
			authorities.add("ROLE_NEWMANAGER");
			result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
			return "employeecreation";
		}

		uniqueUser1 = (userService.getUserProfileByField("email", user.getEmail()));
		if (uniqueUser1.size() > 0) {
			System.out.println("Caught duplicate Email");
			List<String> authorities = new ArrayList<>();
			authorities.add("ROLE_NEWEMPLOYEE");
			authorities.add("ROLE_NEWMANAGER");
			result.rejectValue("email", "DuplicateKeyException.user.email", "Email already exists.");
			return "employeecreation";
		}

		String role = rqst.getParameter("role");
		System.out.println(user);

		Users users = new Users();
		users.setUsername(user.getUsername());
		String tempPassword = UtilityController.generatePassword();
		System.out.println(tempPassword);

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

		Authorities auth = new Authorities();
		auth.setUsername(user.getUsername());
		auth.setAuthority(role);
		userService.createUser(user);
		userService.saveOrUpdateUsers(users);
		userService.setAuthority(auth);

		SendMail sendmail = new SendMail();
		sendmail.sendTempPassword(user.getEmail(), tempPassword, user.getFirstname());

		model.addAttribute("uname", principal.getName());
		return "adminhome";

	}

	@RequestMapping(value = "/employeecreation")
	public String createEmployee(HttpServletRequest rqst, Model model, Principal principal) {
		model.addAttribute("user", new User());
		List<String> authorities = new ArrayList<>();
		authorities.add("ROLE_NEWEMPLOYEE");
		authorities.add("ROLE_NEWMANAGER");
		model.addAttribute("roles", authorities);
		return "employeecreation";

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
	public String editManagerProfile(Model model, Principal principal) {
		String uname = principal.getName();
		User user = userService.getUserregisterbyUsername(uname);
		model.addAttribute("user", user);
		return "editadminprofile";
	}

	@RequestMapping("/editadminprofiledone")
	public String editManagerProfileDone(@Valid User eUser, BindingResult result, Model model) {
		if (result.hasErrors())
			return "editadminprofile";
		User user = userService.getUserregisterbyUsername(eUser.getUsername());
		eUser.setSSN(user.getSSN());
		eUser.setEmail(user.getEmail());
		eUser.setDob(user.getDob());

		userService.createUser(eUser);
		return "adminhome";
	}

	private boolean check_if_authorised_to_delete(String myRole, String userRole) {
		if ("ROLE_ADMIN".equals(myRole)) {
			if ("ROLE_ADMIN".equals(userRole) || "ROLE_USER".equals(userRole) || "ROLE_MERCHANT".equals(userRole))
				return false;
			else
				return true;
		}
		return false;
	}

}
