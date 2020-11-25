package com.java.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description:	   MD5加密工具类<br/>
 * Date:     2020/11/16 20:49 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public class MD5Tool {

    private static final String[] digital = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F"};

    private static String initMD5(String mingwen) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        if (mingwen == null) {
            throw new Exception("请输入明文!");
        }
        byte[] bytes = md5.digest(mingwen.getBytes("UTF-8"));
        StringBuilder miwen = new StringBuilder("");
        for (byte b : bytes) {
            int num = b;
            if (num < 0) {
                num += 256;
            }
            int index1 = num / 16;
            int index2 = num % 16;
            miwen.append(digital[index1]).append(digital[index2]);
        }
        return miwen.toString();

    }

    /**
     * 最终的MD5加密
     *
     * @param mingwen 明文
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String finalMD5(String mingwen) throws Exception {
        return initMD5(initMD5(initMD5(mingwen) + "Aaron") + "Aaron");
    }

}
