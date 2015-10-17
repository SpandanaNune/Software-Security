package sbs.web.utilities;

import java.security.*;
import java.security.spec.*;
import java.util.*;
import java.io.*;

public class MTBCPKIUtility {

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
			System.out.println("Public key1: " + publicPrivateKeyPair.getPublic());
			System.out.println("Private key2: " + publicPrivateKeyPair.getPrivate());
			return publicPrivateKeyPair;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String signMessageWithPrivateKey(String message, PrivateKey privateKey) throws SignatureException {
		try {
			Signature sign = Signature.getInstance(SignatureAlgo);
			sign.initSign(privateKey);
			sign.update(message.getBytes("UTF-8"));
			return new String(Base64.getEncoder().encodeToString(sign.sign()));
		} catch (Exception ex) {
			throw new SignatureException(ex);
		}
	}

	public boolean verifyMessageWithPublicKey(String message, String signature, PublicKey publicKey) throws SignatureException {
		try {
			Signature sign = Signature.getInstance(SignatureAlgo);
			sign.initVerify(publicKey);
			sign.update(message.getBytes("UTF-8"));
			return sign.verify(Base64.getDecoder().decode(signature.getBytes("UTF-8")));
		} catch (Exception ex) {
			throw new SignatureException(ex);
		}
	}

	/**
	 * Returns true if the specified text is encrypted, false otherwise
	 */
	public static boolean isEncrypted(String text) {
		// If the string does not have any separators then it is not
		// encrypted
		if (text.indexOf('-') == -1) {
			/// System.out.println( "text is not encrypted: no dashes" );
			return false;
		}

		StringTokenizer st = new StringTokenizer(text, "-", false);
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.length() > 3) {
				return false;
			}
			for (int i = 0; i < token.length(); i++) {
				if (!Character.isDigit(token.charAt(i))) {
					return false;
				}
			}
		}
		// System.out.println( "text is encrypted" );
		return true;
	}

	public String SaveKeyPair(KeyPair keyPair, String userID) throws IOException {
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		
		String keyPath = createDir(userID);
		
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

	public KeyPair LoadKeyPair(String userID) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Read Public Key.
		String keyPath = defaultPath + userID;
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
	
	private String createDir(String userID){
		String keyPath = defaultPath + userID;
		File new_dir = new File(keyPath);
		if (!new_dir.exists())
		{
			if(new_dir.mkdirs()){
				return keyPath;
			}
		}
		return null;
	}
}
