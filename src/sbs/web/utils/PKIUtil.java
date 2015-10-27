package sbs.web.utils;

import java.security.KeyPair;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import sbs.web.models.User;
import sbs.web.utilities.MTBCPKIUtility;
import sbs.web.utilities.SendMail;

public class PKIUtil {
	private static final Logger logger = Logger.getLogger(PKIUtil.class);
	public static void sendPrivateKey(User user)
	{
		MTBCPKIUtility user_pki = new MTBCPKIUtility();
		KeyPair user_keyPair = user_pki.generatePublicPrivateKeyPairs();
		String keyPath = "";
		String priKeyPath = "";
		try{
			keyPath = user_pki.SaveKeyPair(user_keyPair, user.getUsername());
		}
		catch (Exception e){
			logger.error("Error saving keypair to disk for user:"+user.getUsername());
		}
		try{ 
			FilenameUtils util = new FilenameUtils();
			priKeyPath = util.separatorsToSystem(keyPath + "/private.key");
			SendMail sendMail = new SendMail();
			sendMail.sendPrivateKey(user, priKeyPath);
		}
		catch(Exception e){
			logger.error("Error sending keypair to user:"+user.getUsername());
		}
		if (priKeyPath != null || "".equals(priKeyPath))
			user_pki.deletePrivateKey(priKeyPath);
	}
	
	public static boolean validateKeyPairs(User user, String message)
	{
		boolean result=false;
		FilenameUtils util = new FilenameUtils();
		String keyPath = util.separatorsToSystem(System.getProperty("catalina.home") + "/users_keys/" + user.getUsername());
		MTBCPKIUtility user_pki = new MTBCPKIUtility();
		try{
			KeyPair new_pair = user_pki.LoadKeyPair(user.getUsername()); 
			result = user_pki.verifyMessageWithPublicKey(message, user_pki.signMessageWithPrivateKey(message, new_pair.getPrivate()), new_pair.getPublic());
		}
		catch (Exception e){
			logger.error("Error validating keypair for user:"+user.getUsername());
		}
		user_pki.deletePrivateKey(keyPath + "/private.key");
		return result;
	}
}
