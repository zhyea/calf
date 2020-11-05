package org.chobit.calf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author robin
 */
public final class MD5 {


    private static final Logger logger = LoggerFactory.getLogger(MD5.class);

    private static final String SALT = "#_9a.爨";


    public static String encode(String src) {
        return encode(SALT, src);
    }


    public static String encode(String salt, String src) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update((src + salt).getBytes());
            byte[] bytes = m.digest();
            String result = "";
            for (int i = 0; i < bytes.length; i++) {
                result += Integer.toHexString((0x000000FF & bytes[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            logger.error("Encode with MD5 failed.", e);
        }

        return new String((salt + src).getBytes(), StandardCharsets.US_ASCII);
    }


    private MD5() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}
