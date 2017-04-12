package com.ynaito.encryptionsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    private final static String HEX = "0123456789ABCDEF";
    public final static String PREFERENCE_ID = "hogehoge";
    private static final int ENCRYPT_KEY_LENGTH = 128;

    public static String encryptAES(Context context, String value) {
        Log.d(TAG, "[enter] : encryptAES");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (sp.getString(PREFERENCE_ID, null) == null) {
            Log.d(TAG, "Encrypt key is null. Generate EncryptKey");
            //乱数を生成
            byte[] salt = EncryptionUtil.generateKey();
            sp.edit().putString(EncryptionUtil.PREFERENCE_ID, salt.toString()).commit();
        }
        try {
            byte[] rawKey = sp.getString(PREFERENCE_ID, null).getBytes();
            SecretKey secretKey = new SecretKeySpec(getRawKey(rawKey), "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedString = c.doFinal(value.getBytes("UTF-8"));
            return toHex(encryptedString);
        } catch (Exception e) {
            Log.e(TAG, "Exception from encryptAES", e);
        }
        return null;
    }

    public static String decryptAES(Context context, String value) {
        Log.d(TAG, "[enter] : decryptAES");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (sp.getString(PREFERENCE_ID, null) == null) {
            return "";
        }
        try {
            byte[] rawKey = sp.getString(PREFERENCE_ID, null).getBytes();
            byte[] byteValue = toByte(value);
            SecretKey secretKey = new SecretKeySpec(getRawKey(rawKey), "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedString = c.doFinal(byteValue);
            return new String(decryptedString);
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

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    public static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }


    public static byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[ENCRYPT_KEY_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
}
