package sbs.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sbs.web.models.User;

public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user =(User) target;

		Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Pattern dobPattern = Pattern.compile("^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$");
		Pattern ssnPattern = Pattern.compile("^[0-9]{9}$");
		Pattern phonePattern = Pattern.compile("^[0-9]{10}$");

		Pattern zipPattern = Pattern.compile("^[0-9]{5}$");

		Matcher matcher = null;
		if(user.getUsername()==null || user.getUsername().length()<5 || user.getUsername().length()>45)
			errors.rejectValue("username", "Invalid username","Username must be atleast 5 characters and not greater than 45 characters");
		if(user.getFirstname()==null || user.getFirstname().length()<2 || user.getFirstname().length()>45)
			errors.rejectValue("firstname", "Invalid firstname","Name must be atleast 2 characters and less than 45 characters");
//		if(user.getLastname()==null || user.getLastname().length()<2 || user.getLastname().length()>45)
//			errors.rejectValue("lastname", "Invalid lastname","Name must be atleast 2 characters and less than 45 characters");
		matcher = emailPattern.matcher(user.getEmail());
		if(!matcher.matches())
		{
			errors.rejectValue("email", "Invalid email","Invalid email id format");
		}
		matcher = dobPattern.matcher(user.getDob());
		if(!matcher.matches())
		{
			errors.rejectValue("dob", "Invalid dob","Invalid date of birth");
		}
		matcher = ssnPattern.matcher(user.getSSN());
		if(!matcher.matches())
		{
			errors.rejectValue("SSN", "Invalid SSN","Invalid SSN");
		}
		matcher = phonePattern.matcher(user.getPhone());
		if(!matcher.matches())
		{
			errors.rejectValue("phone", "Invalid Phone","Invalid Phone number");
		}
		
		if(user.getAddr1()==null || user.getAddr1().length()<5 || user.getAddr1().length()>45)
		{
			errors.rejectValue("Addr1", "Invalid address 1","Address1 must be atleast 5 characters and not greater than 45 characters");
		}
		if(user.getAddr2()==null || user.getAddr2().length()<5 || user.getAddr2().length()>45)
		{
			errors.rejectValue("Addr2", "Invalid address 2","Address2 must be atleast 5 characters and not greater than 45 characters");
		}
		
		if(user.getCity()==null || user.getCity().length()<2 || user.getCity().length()>45)
		{
			errors.rejectValue("City", "Invalid City","City must be atleast 2 characters and not greater than 45 characters");
		}
		if(user.getState()==null || user.getState().length()<2 || user.getState().length()>45)
		{
			errors.rejectValue("State", "Invalid State","State must be atleast 2 characters and not greater than 45 characters");
		}
		matcher = zipPattern.matcher(user.getZip());
		if(!matcher.matches())
		{
			errors.rejectValue("Zip", "Invalid Zipcode","Invalid Zipcode format");
		}
	}

}
