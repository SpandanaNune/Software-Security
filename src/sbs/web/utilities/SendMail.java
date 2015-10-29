package sbs.web.utilities;

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

import org.apache.log4j.Logger;

import sbs.web.models.OTP;
import sbs.web.models.User;

public class SendMail {

	private static final Logger logger = Logger.getLogger(SendMail.class);
	
	public static void sendlogs(User user, String filePath) {

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
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(randomMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("System Logs from Money Tree Banking Corporation");
			String msg = "Dear " + user.getUsername()
			+ "\n\n Your System logs are attached"
			
			+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'";
			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(msg, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			MimeBodyPart attachPart = new MimeBodyPart();
			try {
				attachPart.attachFile(filePath);
				logger.info("Sent system logs successfully to user :"+ user.getUsername());
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

	public static void sendStatement(User user, String filePath) {

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
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(randomMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("EStatement from Money Tree Banking Corporation");
			String msg = "Dear " + user.getUsername()
			+ "\n\n Your Money Tree Banking Corporation account e-statement"
			+ "is now being sent to you as a pdf document.\n To open this file"
			+ "you need Adobe Acrobat Reader. If you do not have Adobe Acrobat"
			+ "Reader, please visit the following link to download it:"
			+ "http://www.adobe.com/products/acrobat/readstep2.html."
			+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'";
			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(msg, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			MimeBodyPart attachPart = new MimeBodyPart();
			try {
				attachPart.attachFile(filePath);
				logger.info("Sent estatement successfully to user :"+ user.getUsername());
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

	public void sendTransactionOTP(OTP otpObj){
		
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
							+ "\n\n We are pleased that you chose to bank with MTBC. Your OTP for making the  transfer is "
							+ otpObj.getOtpValue()
							+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'");
					System.out.println("Sending message");
					Transport.send(message);

					System.out.println("Done");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
				
	}
	public void sendPrivateKey(User user, String filePath) {

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
			message.setSubject("Confidential Information from Money Tree Banking Corporation");
			String msg = "Dear " + user.getFirstname() + " " + user.getLastname() + ","
					+ "\n\n Your Money Tree Banking Corporation account has been setup successfully.\n"
					+ "We are happy to have you with us. We have included highly confidential information in this email.\n"
					+ "A private key is sent to you. Please store this File securely and Do not share it.\n"
					+ "The Banking system will explicity request you for the private key, for some banking transactions."
					+ "\n If you have any questions please contact our customer care https://group8.mobicloud.asu.edu/mtbc/contact-us.jsp."
					+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'";
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

	public void resetPasswordLink(User user, String link) {

		// Recipient's email ID needs to be mentioned.
		String to = user.getEmail();
		String uname = user.getFirstname() + user.getLastname();
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
			message.setSubject("Reset Password Link for " + user.getEmail());
			String msg = "Dear " + uname + "," + "\n\n Your  link to rest password is " + link
					+ ".\n The Link is valid for one time use only."
					+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'";
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
	
	public void sendTempPassword(String email, String tempPass, String fname) {
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
			message.setSubject("Welcome To Money Tree Banking Corporation " + fname);
			message.setText("Dear " + fname + ","
					+ "\n\n We are pleased to have you with MTBC bank. "
					+ "To Complete setting up your account please login with your temporary one time password"
					+"\n Temporary Password :" + tempPass 
					+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'");
			System.out.println("Sending message");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sendPIIConfirm(String email,String fname) {
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
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(randomMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("PII Approved - Money Tree Banking Corporation " + fname);
			message.setText("Dear " + fname + ","
					+ "\n\n We are pleased to inform that your PII request has been approved. "
					+"\n\n You can verify your updated profile by logging into our system"
					
					+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'");
			System.out.println("Sending message");
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
		String[] from = {  "moneytreebanking2@gmail.com", "moneytreebanking3@gmail.com",
				"moneytreebanking4@gmail.com", "moneytreebanking5@gmail.com","moneytreebanking@gmail.com", };

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

		int n = rand.nextInt(4) + 1;
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
					+ otpObj.getOtpValue()
					+ "\n\nSincerely,\nMoney Tree Banking Corporation \n 'Grow your money here'");
			System.out.println("Sending message");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
