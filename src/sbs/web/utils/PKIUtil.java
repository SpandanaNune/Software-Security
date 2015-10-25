package sbs.web.utils;

import java.security.KeyPair;

import org.apache.log4j.Logger;

import sbs.web.models.User;
import sbs.web.utilities.MTBCPKIUtility;
import sbs.web.utilities.SendMail;

public class PKIUtil {
	private static final Logger logger = Logger.getLogger(PKIUtil.class);
	public void sendPrivateKey(User user)
	{
		MTBCPKIUtility user_pki = new MTBCPKIUtility();
		KeyPair user_keyPair = user_pki.generatePublicPrivateKeyPairs();
		String keyPath = "";
		try{
			keyPath = user_pki.SaveKeyPair(user_keyPair, user.getUsername());
		}
		catch (Exception e){
			logger.error("Error saving keypair to disk for user:"+user.getUsername());
		}
		try{ 
			keyPath = keyPath + "private.key";
			SendMail sendMail = new SendMail();
			sendMail.sendPrivateKey(user, keyPath);
		}
		catch(Exception e){
			logger.error("Error sending keypair to user:"+user.getUsername());
		}
	}
	
	public boolean validateKeyPairs(User user, String message)
	{
		MTBCPKIUtility user_pki = new MTBCPKIUtility();
		try{
			KeyPair new_pair = user_pki.LoadKeyPair(user.getUsername()); 
			boolean result = user_pki.verifyMessageWithPublicKey(message, user_pki.signMessageWithPrivateKey(message, new_pair.getPrivate()), new_pair.getPublic());
			return result;
		}
		catch (Exception e){
			logger.error("Error validating keypair for user:"+user.getUsername());
		}
		return false;
	}
}
