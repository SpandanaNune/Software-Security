package sbs.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
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
import sbs.web.utilities.Compressor;
import sbs.web.utilities.SendMail;

@Controller
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class);
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/systemlogs")
	public String viewSystemLogs(Model model,Principal principal) {
		Compressor comp = new Compressor();
		String outputFile = FilenameUtils.separatorsToSystem(System.getProperty("catalina.home") + "/temp/logs_" + System.currentTimeMillis() + ".zip");
		String sourceFolder = FilenameUtils.separatorsToSystem(System.getProperty("catalina.home") + "/logs");
		System.out.println(outputFile);
		System.out.println(sourceFolder);

		comp.compressFiles(outputFile, sourceFolder);
		User user = userService.getUserregisterbyUsername(principal.getName());
		SendMail.sendlogs(user, outputFile);
		
		System.out.println("Compression Done");
		return "adminhome";
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
	public String editemployee(Model model, @RequestParam("View/Edit") String username, Principal principal) {
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {
			User user = userService.getUserregisterbyUsername(username);
			model.addAttribute("user", user);
			return "employeeUpdation";
		}
		return "accessdenied";
	}

	@RequestMapping("/employeeupdationdone")
	public String employeeUpdate(@Valid User eUser, BindingResult result, Principal principal) {
		if (result.hasErrors())
			return "employeeUpdation";
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", eUser.getUsername());

		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {

			User user = userService.getUserregisterbyUsername(eUser.getUsername());
			eUser.setSSN(user.getSSN());
			eUser.setEmail(user.getEmail());
			eUser.setDob(user.getDob());
			userService.createUser(eUser);
			return "adminhome";
		}
		return "accessdenied";
	}

	@RequestMapping("/deleteemployee")
	public String deleteEmployee(Model model, @RequestParam("Decline") String username, Principal principal) {
		
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_delete(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {

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
		return "accessdenied";

	}

	@RequestMapping(value = "/employeecreation")
    public String createEmployee(HttpServletRequest rqst, @Valid User user, BindingResult result, Model model,
            Principal principal) {
        if (result.hasErrors()) {
            List<String> authorities = new ArrayList<>();
            authorities.add("ROLE_NEWEMPLOYEE");
            authorities.add("ROLE_NEWMANAGER");
            model.addAttribute("roles", authorities);
            return "employeecreation";
        }

        if (user != null && user.getUsername() != null) {
        List<User> uniqueUser1;
        uniqueUser1 = (userService.getUserProfileByField("username", user.getUsername().toLowerCase()));
        System.out.println("uniqueUser " + uniqueUser1);
        if (uniqueUser1.size() > 0) {
            System.out.println("Caught duplicate Username");
            List<String> authorities = new ArrayList<>();
            authorities.add("ROLE_NEWEMPLOYEE");
            authorities.add("ROLE_NEWMANAGER");
            model.addAttribute("roles", authorities);
            result.rejectValue("username", "DuplicateKeyException.user.username", "Username already exists.");
            return "employeecreation";
        }

        uniqueUser1 = (userService.getUserProfileByField("email", user.getEmail()));
        if (uniqueUser1.size() > 0) {
            System.out.println("Caught duplicate Email");
            List<String> authorities = new ArrayList<>();
            authorities.add("ROLE_NEWEMPLOYEE");
            authorities.add("ROLE_NEWMANAGER");
            model.addAttribute("roles", authorities);
            result.rejectValue("email", "DuplicateKeyException.user.email", "Email already exists.");
            return "employeecreation";
        }

        
            String role = rqst.getParameter("role");
            if (role.equals("ROLE_NEWEMPLOYEE") || role.equals("ROLE_NEWMANAGER")){
	            System.out.println(user);
	
	            // User uniqueUser =
	            // (userService.getUserregisterbyUsername(user.getUsername()));
	            // if (uniqueUser == null) {
	            // System.out.println(user);
	            // user.setIsnewuser(true);
	
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
	            return "adminhome";
            }
            else{
            	return "accessdenied";
            }

        }
        

        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_NEWEMPLOYEE");
        authorities.add("ROLE_NEWMANAGER");
        model.addAttribute("roles", authorities);
        model.addAttribute("uname", principal.getName());
        return "employeecreation";

    }

	@RequestMapping(value = "/pii")
	public String listPIIs(Model model) {
		List<PII> piis = userService.getAllPIIs();
		model.addAttribute("piis", piis);
		return "pii";
	}

	@RequestMapping("/acceptpii")
	public String acceptUserPII(Model model, @RequestParam("Accept") String username, Principal principal) {
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_approve_pii(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {

			User user = userService.getUserregisterbyUsername(username);
			PII pii = userService.getPII(username);
			if (pii.getOldSSN().equalsIgnoreCase(user.getSSN())) {
				user.setSSN(pii.getNewSSN());
				userService.updateUser(user);
				userService.approvePII(pii.getUserName());
				SendMail.sendPIIConfirm(user.getEmail(),user.getFirstname());
			} else {
				// error SSN not matched
			}
			List<PII> piis = userService.getAllPIIs();
			model.addAttribute("piis", piis);
			return "pii";
		}
		return "accessdenied";
	}

	@RequestMapping("/declinepii")
	public String declineUserPII(Model model, @RequestParam("Decline") String username, Principal principal) {
		Authorities logged_in_user_auth = userService.getAuthorityByField("username", principal.getName());
		Authorities edited_user_auth = userService.getAuthorityByField("username", username);

		boolean canDelete = check_if_authorised_to_approve_pii(logged_in_user_auth.getAuthority(),
				edited_user_auth.getAuthority());
		if (canDelete) {

			userService.deletePII(username);
			List<PII> piis = userService.getAllPIIs();
			model.addAttribute("piis", piis);
			return "pii";
		}
		return "accessdenied";
	}

	@RequestMapping("/editadminprofile")
	public String editManagerProfile(Model model, Principal principal) {
		String uname = principal.getName();
		User user = userService.getUserregisterbyUsername(uname);
		model.addAttribute("user", user);
		return "editadminprofile";
	}

	@RequestMapping("/editadminprofiledone")
	public String editManagerProfileDone(@Valid User eUser, BindingResult result, Model model, Principal principal) {
		if (result.hasErrors())
			return "editadminprofile";
		boolean canEdit = principal.getName().equals(eUser.getUsername());
		if (canEdit){
			User user = userService.getUserregisterbyUsername(eUser.getUsername());
			eUser.setSSN(user.getSSN());
			eUser.setEmail(user.getEmail());
			eUser.setDob(user.getDob());
	
			userService.createUser(eUser);
			return "adminhome";
		}
		return "accessdenied";
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
	
	private boolean check_if_authorised_to_approve_pii(String myRole, String userRole) {
		if ("ROLE_ADMIN".equals(myRole)) {
			if ("ROLE_USER".equals(userRole) || "ROLE_MERCHANT".equals(userRole))
				return true;
			else
				return false;
		}
		return false;
	}


}

