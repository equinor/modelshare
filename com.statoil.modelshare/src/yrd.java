import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class yrd {
	public static void main(String[] args) {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();
			Base64.Encoder encoder = Base64.getEncoder();
			String encryptedText = encoder.encodeToString(secretKey.getEncoded());
			System.out.println(encryptedText);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cipher = Cipher.getInstance("AES");
	}
}
