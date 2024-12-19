package com.sy.common.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author Monster
 * @version v1.0
 */
public class MD5Utils {

    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private final static String ALGORITHM = "MD5";

    public static String md5Encode(String origin) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        return byteArrayToHexString(md.digest(origin.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    /**
     * 转换字节为16进制字符
     *
     * @param b 字节
     * @return 16进制字符
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
