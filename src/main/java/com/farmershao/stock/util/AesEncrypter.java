package com.farmershao.stock.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * AESEncrypter
 *
 * @author Shao Yu
 * @date 2019/5/5 19:19
 * @since
 **/
@Slf4j
public class AesEncrypter {


    /**
     * 加密
     * @param msg
     * @param key
     * @return
     * @throws Exception
     */
    private static String encrypt(String msg, String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //需要自己手动设置
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        kgen.init(128, random);
        SecretKey generateKey = kgen.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, generateKey);
        return asHex(cipher.doFinal(msg.getBytes()));
    }

    private static String decrypt(String value, String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //需要自己手动设置
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        kgen.init(128, random);
        SecretKey genKey = kgen.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, genKey);
        return new String(cipher.doFinal(asBin(value)));
    }

    private static String asHex(byte buf[]) {
        StringBuilder sb = new StringBuilder(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return sb.toString();
    }

    private static byte[] asBin(String src) {
        if (src.length() < 1){
            return null;
        }
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    /**
     * 加密
     * @param src
     * @param key
     * @return
     */
    public static String getEncrypt(String src, String key) {
        if(!StringUtils.isEmpty(src)) {
            try {
                src = encodeBase64(src);
                return encrypt(src, key);
            } catch (Exception e) {
                log.error("aes encrypt error, source: {}, msg: {}", new Object[]{src, e.getMessage()});
            }
        }
        return null;
    }

    /**
     * 解密
     * @param src
     * @param key
     * @return
     */
    public static String getDecrypt(String src, String key) {
        if(!StringUtils.isEmpty(src)) {
            try {
                src = decrypt(src, key);
                return decodeBase64(src);
            } catch (Exception e) {
                log.error("aes decrypt error, source: {}, msg: {}", new Object[]{src, e.getMessage()});
            }
        }
        return null;
    }

    private static String encodeBase64(String str) throws UnsupportedEncodingException {
        return new String(Base64.encodeBase64(str.getBytes("utf8")));
    }

    private static String decodeBase64(String str) throws UnsupportedEncodingException{
        return new String(Base64.decodeBase64(str), "utf8");
    }
}
