package org.samye.dzong.london;

import org.apache.commons.codec.binary.*;

public class Base64Wrapper {
    public static String decode(String value) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(value));
    }

    public static String encode(String value) {
        return StringUtils.newStringUtf8(Base64.encodeBase64(value.getBytes(), true, false,Integer.MAX_VALUE));
    }

    public static String decodeToHex(String value) {
        return new String(Hex.encodeHex(Base64.decodeBase64(value), true));
    }

    public static String encodeToBase64AsHex(String value) {
        return new String(Hex.encodeHex(Base64.encodeBase64(value.getBytes(), true)));
    }
}