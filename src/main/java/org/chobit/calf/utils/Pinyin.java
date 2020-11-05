package org.chobit.calf.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * @author robin
 */
public final class Pinyin {

    private static final String DEFAULT_BLANK = "";

    public static String firstChar(String str) {
        if (null == str) {
            return DEFAULT_BLANK;
        }
        str = str.trim();
        if (str.length() == 0) {
            return DEFAULT_BLANK;
        }
        try {
            char c = str.charAt(0);
            if (Character.isDigit(c)) {
                return String.valueOf(c);
            }
            if (c >= 'A' && c <= 'z') {
                return String.valueOf(c).toUpperCase();
            }
            String[] arr = PinyinHelper.toHanyuPinyinStringArray(c);
            return arr[0].substring(0, 1).toUpperCase();
        } catch (Exception e) {
            return DEFAULT_BLANK;
        }
    }


    private Pinyin() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}
