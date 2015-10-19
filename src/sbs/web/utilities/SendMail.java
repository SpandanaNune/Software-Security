package sbs.web.utilities;

import org.apache.log4j.Logger;
import java.io.IOException;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import sbs.web.models.OTP;
import sbs.web.models.User;

public class SendMail {
	
	private static final Logger logger = Logger.getLogger(SendMail.class);

	public static void sendStatement(User user,String filePath) {

		// Recipient's email ID needs to be mentioned.
		String to = user.getEmail();

		// Sender's email ID needs to be mentioned
		String[] from = { "moneytreebanking@gmail.com", "moneytreebanking2@gmail.com", "moneytreebanking3@gmail.com",
				"moneytreebanking4@gmail.com", "moneytreebanking5@gmail.com" };

		final String username = "moneytreebanking";// change accordingly
		final String password = "mtbc1234";// change accordingly
		Properties props = new Properties();

		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Random rand = new Random();

		int n = rand.nextInt(2) + 1;
		// 5 is the maximum and the 1 is our minimum

		String randomMail = from[n - 1];
		System.out.println(randomMail);
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(randomMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("EStatement from Money Tree Banking Corporation");
			String msg = "Dear ";// + user.getUsername() + ","
//					+ "\n\n Your  Money Tree Banking Corporation account e-statement is now being sent to you as a pdf document.\n To open this file, you need Adobe Acrobat Reader. If you do not have Adobe Acrobat Reader, please visit the following link to download it: www.adobe.com/products/acrobat/readstep2.html. \nSincerely,\nMoney Tree Banking Corporation ";
			   // creates message part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(msg, "text/html");
	 
	        // creates multi-part
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	        
			 MimeBodyPart attachPart = new MimeBodyPart();
             try {
                 attachPart.attachFile(filePath);
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
             multipart.addBodyPart(attachPart);
			System.out.println("Sending message");
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	public static void resetPasswordLink(String uname, String email,String link) {
		
		// Recipient's email ID needs to be mentioned.
		String to = email;

		// Sender's email ID needs to be mentioned
		String[] from = { "moneytreebanking@gmail.com", "moneytreebanking2@gmail.com", "moneytreebanking3@gmail.com",
				"moneytreebanking4@gmail.com", "moneytreebanking5@gmail.com" };

		final String username = "moneytreebanking";// change accordingly
		final String password = "mtbc1234";// change accordingly
		Properties props = new Properties();

		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Random rand = new Random();

		int n = rand.nextInt(2) + 1;
		// 5 is the maximum and the 1 is our minimum

		String randomMail = from[n - 1];
		System.out.println(randomMail);
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(randomMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Reset Password Link");
			String msg = "Dear " + uname + ","
					+ "\n\n Your  link to rest password is " + link +".\n It is valid for one time use. \nSincerely,\nMoney Tree Banking Corporation ";
			   // creates message part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(msg, "text/html");
	 
	        // creates multi-part
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	        
			System.out.println("Sending message");
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendOTP(OTP otpObj) {
		// Recipient's email ID needs to be mentioned.
		String to = otpObj.getMailID();

		// Sender's email ID needs to be mentioned
		String[] from = { "moneytreebanking@gmail.com", "moneytreebanking2@gmail.com", "moneytreebanking3@gmail.com",
				"moneytreebanking4@gmail.com", "moneytreebanking5@gmail.com" };

		final String username = "moneytreebanking";// change accordingly
		final String password = "mtbc1234";// change accordingly
		Properties props = new Properties();

		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Random rand = new Random();

		int n = rand.nextInt(2) + 1;
		// 5 is the maximum and the 1 is our minimum

		String randomMail = from[n - 1];
		System.out.println(randomMail);
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(randomMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Welcome To Money Tree Banking Corporation " + otpObj.getFirstName());
			message.setText("Dear " + otpObj.getFirstName() + ","
					+ "\n\n We are pleased that you chose to bank with MTBC. Your OTP for creating your online account is "
					+ otpObj.getOtpValue());
			System.out.println("Sending message");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
