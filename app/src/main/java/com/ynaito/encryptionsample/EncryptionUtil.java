package com.ynaito.encryptionsample;

import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by yuta1723 on 2017/04/12.
 */

public class EncryptionUtil {
    static String TAG = EncryptionUtil.class.getSimpleName();

    private static final int ENCRYPT_KEY_LENGTH = 128;

    public static byte[] encryptAES(String raw, String plain) {
        Log.d(TAG, "[enter] : encryptAES");
        return encryptAES(raw.getBytes(), plain.getBytes());
    }

    public static byte[] encryptAES(byte[] raw, byte[] plain) {
        Log.d(TAG, "[enter] : encryptAES");
        try {
            SecretKey secretKey = new SecretKeySpec(getRawKey(raw), "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, secretKey);
            return c.doFinal(plain);
        } catch (Exception e) {
            Log.e(TAG, "Exception from encryptAES", e);
        }
        return null;
    }

    public static byte[] decryptAES(String raw, String plain) {
        Log.d(TAG, "[enter] : decryptAES");
        return decryptAES(raw.getBytes(), plain.getBytes());
    }

    public static byte[] decryptAES(byte[] raw, byte[] plain) {
        Log.d(TAG, "[enter] : decryptAES");
        try {
            SecretKey secretKey = new SecretKeySpec(getRawKey(raw), "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, secretKey);
            return c.doFinal(plain);
        } catch (Exception e) {
            Log.e(TAG, "Exception from decryptAES", e);
        }
        return null;
    }

    public static byte[] getRawKey(byte[] key) {
        byte[] raw = new byte[16];
        for (int i = 0; i < raw.length; i++) {
            if (key.length >= i + 1) {
                raw[i] = key[i];
            } else {
                raw[i] = '0';
            }
        }
        return raw;
    }

    public static byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[ENCRYPT_KEY_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static String byteToString(byte[] text) {
        return Base64.encodeToString(text, Base64.DEFAULT);
    }

    public static byte[] StringToByte(String text) {
        return Base64.decode(text, Base64.DEFAULT);
    }
}
