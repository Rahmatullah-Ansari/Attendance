package com.android.attendance;
import android.annotation.SuppressLint;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
@SuppressWarnings("ALL")
public class Security {
    private static final byte[] key ={9,15,51,86,100,4,-31,-23,-68,89,37,50,13,-105,19,-73};
    private static Cipher encode;
    private static Cipher decode;
    private static SecretKeySpec secretKeySpec;
    @SuppressLint ("GetInstance")
    public static String Encrypt(String text) throws NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        try {
            encode=Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        secretKeySpec=new SecretKeySpec(key,"AES");
        byte [] message=text.getBytes();
        byte [] encrypted=new byte[message.length];
        try {
            encode.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            encrypted=encode.doFinal(message);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        String encryptedText= null;
        encryptedText = new String(encrypted, StandardCharsets.ISO_8859_1);
        return encryptedText;
    }
    @SuppressLint ("GetInstance")
    public static String Decrypt(String text) throws NoSuchPaddingException {
        try {
            decode=Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        secretKeySpec=new SecretKeySpec(key,"AES");
        byte []message=text.getBytes(StandardCharsets.ISO_8859_1);
        String decryptedString=text;
        byte[] decryptedText;
        try {
            decode.init(Cipher.DECRYPT_MODE,secretKeySpec);
            decryptedText = decode.doFinal(message);
            decryptedString=new String(decryptedText);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return decryptedString;
    }
}