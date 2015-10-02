package sbs.web.utilities;
import java.util.Random;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sbs.web.models.OTP;

public class SendMail {
	
	   public void sendOTP(OTP otpObj)
	   {    
			      // Recipient's email ID needs to be mentioned.
			      String to = otpObj.getMailID();
			      
			   // Sender's email ID needs to be mentioned
			      String[] from = {"moneytreebanking@gmail.com", "moneytreebanking2@gmail.com", "moneytreebanking3@gmail.com", "moneytreebanking4@gmail.com", "moneytreebanking5@gmail.com"};
			      
			      final String username = "moneytreebanking";//change accordingly
			      final String password = "mtbc1234";//change accordingly
			      Properties props = new Properties();

			      props = new Properties();
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.starttls.enable", "true");
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.port", "587");

					Session session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });

					Random rand = new Random();

					int  n = rand.nextInt(2) + 1;
					//5 is the maximum and the 1 is our minimum 
					
					String randomMail=from[n-1];
					System.out.println(randomMail);
					try {

						Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress(randomMail));
						message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(to));
						message.setSubject("Welcome To Money Tree Banking Corporation "+otpObj.getFirstName());
						message.setText("Dear "+otpObj.getFirstName()+","
							+ "\n\n We are pleased that you chose to bank with MTBC. Your OTP for creating your online account is "+otpObj.getOtpValue());
						System.out.println("Sending message");
						Transport.send(message);

						System.out.println("Done");

					} catch (MessagingException e) {
						throw new RuntimeException(e);
					}		   
	   }
}
