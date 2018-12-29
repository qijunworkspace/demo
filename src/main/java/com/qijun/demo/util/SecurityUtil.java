package com.qijun.demo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 常用的加解密工具类
 * 单向加密：MD5、SHA
 * 对称加密：DES、AES
 * 非对称加密：RSA 未引入
 *
 * @author Qijun
 * @date 12/13/18 4:33 PM
 * @version 1.0
 */
public class SecurityUtil {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(SecurityUtil.class);


    /**
     * 单向加密
     */
    public static final String MD5 = "MD5";
    /**
     * 单向加密
     */
    public static final String SHA = "SHA-1";
    /**
     * 对称加密
     */
    public static final String DES = "DES";
    /**
     * 对称加密
     */
    public static final String AES = "AES";


    /**
     * 加密盐，单向加密使用
     */
    private static final String SALT = "qijunworkspace";
    /**
     * 对称加密的密钥，经过base64编程的密钥字符串
     */
    private static final String DES_KEY = "xzS62vH+Dt8=";
    /**
     * 对称加密的密钥，经过base64编程的密钥字符串
     */
    private static final String AES_KEY = "P9Yt5r7AcAwC6iShlt52UQ==";
    /**
     * AES 对称密钥长度
     */
    private static final int KEY_SIZE = 128;


    /**
     * 使用 MD5 或 SHA-1算法加密
     * @param data  明文
     * @param algorithm 算法
     * @return 加密结果
     */
    public static String encryptHash(String data, String algorithm){
        if (!StringUtils.isEmpty(data)){
            byte[] bytes = data.getBytes();
            // 获得MD5摘要算法的 MessageDigest对象
            try {
                MessageDigest md5Digest = MessageDigest.getInstance(algorithm);
                md5Digest.update(SALT.getBytes());
                byte[] hashed = md5Digest.digest(bytes);
                return parseByteArray2Hex(hashed);
            } catch (NoSuchAlgorithmException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }


    /**
     * base64 编码
     * @param data 编码字节组
     * @return String
     */
    public static String encoderBASE64(byte[] data) {
        if (!StringUtils.isEmpty(data)){
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encodeBuffer(data);
        }
        return null;
    }

    /**
     * base64解码
     * @param data 解码字符串
     * @return 字节数组
     */
    public static byte[] decoderBASE64(String data) {
        if (!StringUtils.isEmpty(data)){
            BASE64Decoder base64Decoder = new BASE64Decoder();
            try {
                return base64Decoder.decodeBuffer(data);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }


    /**
     * DES 对称加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encryptDES(byte[] data){
        if (!StringUtils.isEmpty(data)){
            try {
                SecretKey desKey = getDESKey();
                Cipher cipher = Cipher.getInstance(DES);
                cipher.init(Cipher.ENCRYPT_MODE, desKey);
                return new String(cipher.doFinal(data));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * DES 对称解密
     * @param data 密文
     * @return 解密结果
     */
    public static byte[] decryptDES(byte[] data){
        if (!StringUtils.isEmpty(data)){
            try {
                SecretKey deskey = getDESKey();
                Cipher cipher = Cipher.getInstance(DES);
                cipher.init(Cipher.DECRYPT_MODE, deskey);
                return cipher.doFinal(data);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }


    /**
     * AES 加密
     * @param data 明文
     * @return 加密结果
     */
    public static String encryptAES(byte[] data){
        if (!StringUtils.isEmpty(data)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(decoderBASE64(AES_KEY), AES);
            try {
                Cipher cipher = Cipher.getInstance(AES);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                return new String(cipher.doFinal(data));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * AES 解密
     * @param data 密文
     * @return 解密结果
     */
    public static byte[] decryptAES(byte[] data) {
        if(!StringUtils.isEmpty(data)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(decoderBASE64(AES_KEY), AES);
            try {
                Cipher cipher = Cipher.getInstance(AES);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                return cipher.doFinal(data);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * byte -> hex
     * @param data 转换数据
     * @return 16进制字符串
     */
    private static String parseByteArray2Hex(byte[] data) {

        if (!StringUtils.isEmpty(data)){
            StringBuffer hex = new StringBuffer();
            for(int i = 0; i < data.length; i++) {
                int h = data[i] & 0XFF;
                if(h < 16) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(h));
            }
            return hex.toString();
        }
        return null;
    }

    /**
     * hex -> byte
     * @param hex 16进制字符串
     * @return byte数组
     */
    private static byte[] parseHex2ByteArray(String hex) {

        if (!StringUtils.isEmpty(hex)){
            int length = hex.length() >> 1;
            byte[] data = new byte[length];
            for(int i=0; i <length; i++) {
                int n = i << 1;
                int height = Integer.valueOf(hex.substring(n, n + 1), 16);
                int low = Integer.valueOf(hex.substring(n + 1, n + 2), 16);
                data[i] = (byte)(height * 16 + low);
            }
            return data;
        }
        return null;
    }

    /**
     * 获取DES的密钥对象
     * @return 密钥对象
     */
    private static SecretKey getDESKey() throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(decoderBASE64(DES_KEY));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES);
        return secretKeyFactory.generateSecret(desKeySpec);
    }


    /**
     * 用于生成对称密钥
     * @param algorithm 算法
     * @return 密文
     * @throws NoSuchAlgorithmException
     */
    @SuppressWarnings(value = "unused")
    private static String genSecretKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecureRandom secureRandom = new SecureRandom();
        if (AES.equals(algorithm)){
            keyGenerator.init(KEY_SIZE, secureRandom);
        }else{
            keyGenerator.init(secureRandom);
        }
        SecretKey key = keyGenerator.generateKey();
        return encoderBASE64(key.getEncoded());
    }

}
