package com.mx.ProyectoChakray.security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "Carlos_Salado123"; // 16 caracteres para AES-128

    private static String cipherOperation(String input, int mode) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(mode, key);
            byte[] outputBytes = (mode == Cipher.ENCRYPT_MODE)
                ? cipher.doFinal(input.getBytes())
                : cipher.doFinal(Base64.getDecoder().decode(input));
            return (mode == Cipher.ENCRYPT_MODE)
                ? Base64.getEncoder().encodeToString(outputBytes)
                : new String(outputBytes);
        } catch (Exception e) {
            return null;
            
        }
    }

    public static String encrypt(String plainText) {
        return cipherOperation(plainText, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(String encryptedText) {
        return cipherOperation(encryptedText, Cipher.DECRYPT_MODE);
    }
}
