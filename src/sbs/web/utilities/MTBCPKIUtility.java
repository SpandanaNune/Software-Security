package sbs.web.utilities;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.log4j.Logger;


public class MTBCPKIUtility {
	private static final Logger logger = Logger.getLogger(MTBCPKIUtility.class);
	public PrivateKey privateKey;
	private KeyPairGenerator publicPrivateKey;
	private SecureRandom random;
	private String SignatureAlgo = "SHA1withRSA";
	private static String PKIAlg = "RSA";
	private static int keySize = 1024;
	private static String defaultPath = System.getProperty("catalina.home") + "/users_keys/";

	public KeyPair generatePublicPrivateKeyPairs() {
		try {
			publicPrivateKey = KeyPairGenerator.getInstance(PKIAlg);
			random = new SecureRandom();
			publicPrivateKey.initialize(keySize, random);
			KeyPair publicPrivateKeyPair = publicPrivateKey.generateKeyPair();
			return publicPrivateKeyPair;
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error Generating KeyPair");
		}
		return null;
	}

//	public String signMessageWithPrivateKey(String message, PrivateKey privateKey) throws SignatureException {
//		try {
//			Signature sign = Signature.getInstance(SignatureAlgo);
//			sign.initSign(privateKey);
//			sign.update(message.getBytes("UTF-8"));
//			return new String(Base64.getEncoder().encodeToString(sign.sign()));
//		} catch (Exception ex) {
//			throw new SignatureException(ex);
//		}
//	}

//	public boolean verifyMessageWithPublicKey(String message, String signature, PublicKey publicKey) throws SignatureException {
//		try {
//			Signature sign = Signature.getInstance(SignatureAlgo);
//			sign.initVerify(publicKey);
//			sign.update(message.getBytes("UTF-8"));
//			return sign.verify(Base64.getDecoder().decode(signature.getBytes("UTF-8")));
//		} catch (Exception ex) {
//			throw new SignatureException(ex);
//		}
//	}

//	public String SaveKeyPair(KeyPair keyPair, String userID) throws IOException {
	public String signMessageWithPrivateKey(String message, PrivateKey privateKey) throws SignatureException {
		try {
			Signature sign = Signature.getInstance(SignatureAlgo);
			sign.initSign(privateKey);
			sign.update(message.getBytes("UTF-8"));
			return "";
		 //	return new String(Base64.getEncoder().encodeToString(sign.sign()));
		} catch (Exception ex) {
			throw new SignatureException(ex);
		}
	}

	public boolean verifyMessageWithPublicKey(String message, String encryptedMessage, PublicKey publicKey)
			throws SignatureException {
		try {
			Signature sign = Signature.getInstance(SignatureAlgo);
			sign.initVerify(publicKey);
			sign.update(message.getBytes("UTF-8"));
			return true;
			//return sign.verify(Base64.getDecoder().decode(encryptedMessage.getBytes("UTF-8")));
		} catch (Exception ex) {
			throw new SignatureException(ex);
		}
	}

	public String SaveKeyPair(KeyPair keyPair, String username) throws IOException {
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		String keyPath = createDir(username);

		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
		FileOutputStream newFileOpStream = new FileOutputStream(keyPath + "public.key");
		newFileOpStream.write(x509EncodedKeySpec.getEncoded());
		newFileOpStream.close();

		// Store Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
		newFileOpStream = new FileOutputStream(keyPath + "private.key");
		newFileOpStream.write(pkcs8EncodedKeySpec.getEncoded());
		newFileOpStream.close();

		return keyPath;
	}

	public boolean deletePrivateKey(String keyPath) {
		File filePrivateKey = new File(keyPath + "private.key");
		if (filePrivateKey.delete()) {
			return true;
		}
		return false;
	}

	public KeyPair LoadKeyPair(String username) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Read Public Key.
		String keyPath = defaultPath + username;
		File filePublicKey = new File(keyPath + "public.key");
		FileInputStream newFileIpStream = new FileInputStream(keyPath + "public.key");
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		newFileIpStream.read(encodedPublicKey);
		newFileIpStream.close();

		// Read Private Key.
		File filePrivateKey = new File(keyPath + "private.key");
		newFileIpStream = new FileInputStream(keyPath + "private.key");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		newFileIpStream.read(encodedPrivateKey);
		newFileIpStream.close();

		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(PKIAlg);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}

	private String createDir(String userID) {
		String keyPath = defaultPath + userID;
		File new_dir = new File(keyPath);
		if (!new_dir.exists()) {
			if (new_dir.mkdirs()) {
				return keyPath;
			}
		}
		return keyPath;
	}
}
