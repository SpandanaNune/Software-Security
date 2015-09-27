package sbs.web.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utilities {
	
	 public static String generateRandomAlphaNumeric() {
			 SecureRandom random = new SecureRandom();
		    return new BigInteger(15, random).toString(32);
		  }
	
	public static String hash_SHA(String message)
	{
		MessageDigest md;
		String hash = null;
		try {
			md = MessageDigest.getInstance("SHA-256");

			md.update(message.getBytes("UTF-8"));
			byte[] digest = md.digest();
			
			 hash =String.format("%064x", new java.math.BigInteger(1, digest));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return hash;
	}
	

}
