package com.dictionary.Controllers.Welcome;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String md5HashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(input.getBytes());

            byte[] digest = md.digest();

            BigInteger number = new BigInteger(1, digest);
            String hashedString = number.toString(16);

            while (hashedString.length() < 32) {
                hashedString = "0" + hashedString;
            }

            return hashedString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
