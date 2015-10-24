package sbs.web.controllers;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sbs.web.models.OTP;
import sbs.web.models.User;
import sbs.web.service.UtilityService;
import sbs.web.utilities.SendMail;

@Controller
public class UtilityController {
	private UtilityService utilityService;

	@Autowired
	public void setUtilityService(UtilityService utilityService) {
		this.utilityService = utilityService;
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
	
	@RequestMapping("/sendOTP")
	public String verifyUserMailID(User user, Model model) {
		System.out.println("showViewUser");
//		String mail=user.getEmail();
		String mail="Mallikarjunbpbp@gmail.com";
		String firstName="Mallikarjun";
		//generate otp
		String otp=generatePassword();
		System.out.println("otp "+otp);
		
		OTP otpObj = new OTP();
		otpObj.setFirstName(firstName);
		otpObj.setMailID(mail);
		otpObj.setOtpValue(otp);
//		otpObj.setTimeStamp(new Date());
		try{
			utilityService.insertOTP(otpObj); 
			SendMail sendMail = new SendMail();
			sendMail.sendOTP(otpObj);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return "homepage";
		
	}
	
	@RequestMapping("/verifyOTP")
	public String verifyUserOTP(User user, Model model) {
		System.out.println("showViewUser");
//		String mail=user.getEmail();
		String mail="Mallikarjunbpbp@gmail.com";
		String firstName="Mallikarjun";
		String otp="8T6DIJ9j";
		OTP otpObj = new OTP();
		otpObj.setFirstName(firstName);
		otpObj.setMailID(mail);
		otpObj.setOtpValue(otp);
		try{
			OTP dbObj= utilityService.checkOTP(otpObj); 
			if(dbObj==null){
				System.out.println("bdObj is null. Go to error page");
				//Go to error page
				return "homepage";
			}
			else{
				System.out.println("DB Object "+dbObj.getFirstName()+" "+dbObj.getMailID()+" "+dbObj.getOtpValue());
				System.out.println("otpObj.getOtpValue() "+otpObj.getOtpValue());
				
				if(otpObj.getOtpValue().equals(dbObj.getOtpValue())){
					System.out.println("Correct OTP. Navigate to required page");
//					utilityService.deleteOTP(otpObj);
					return "homepage";
				}
				else if(dbObj.getAttempts()==2){
					System.out.println("Too many attempts. Deleting the OTP. dbObj.getAttempts() "+dbObj.getAttempts());
					utilityService.deleteOTP(otpObj);
					return "homepage";
				}else{
					int attempts=dbObj.getAttempts();
					otpObj.setAttempts(attempts+1);
					utilityService.updateOTP(otpObj);
				}
			
				return "homepage";
			}
				
		}
		catch(Exception e){
			System.out.println("Printing stack trace");
			e.printStackTrace();
		}

		return "homepage";
	}

	
	

}
