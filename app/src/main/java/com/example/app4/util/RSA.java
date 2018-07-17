package com.example.app4.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RSA {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";

    public static void main(String[] args) throws Exception {
//        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWUcmIIOb3lW1HlDa4TvZy5mhE/gr+2kEgoXtE+xaf07iAWA+TSyCHB1hQzoAPJScCmf9N4+oyABVZa6aZzgjHeIEYf5hB92hc9mHHymjbGFkJOhLUniGB4PoR5x0x8vm0f1wyOicplVJeSoXx2eNjLyv6a7q56/n+/9MelupNXQIDAQAB";
//        String pri = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJxdMhziQZXqw7rSB7vZmPAYhQCpYUI8EYDlX7C/y0H6nBQJbqCm9oLBkdxLjJpRZWCKNlwjkqhYncxqRxkXXwLToGXn845AWXdOJMB6Kn9MY3LGPLJTEzO30DdRgvDZMwrYJc/9Elc/k6Uy8KoYOSijItL2jf5COWLlKO3cctTZAgMBAAECgYB6wGsL489rGs8nhaTOoud+bTFUsKQKtE9laixx/E9b75rB3Bm+AvT4929DiwfJSecVH8vHD6IdiNvwUuqX1xIdUcVMXon2xZy3SXJKs9233hthKZ8t6jjpfgDXgXQRQx2pP/6LfsDp3SuGXmAWIJUdpMjjRdZQOkipvztgut6RAQJBANk5PZFcJPGkzNRcaiSS2J03t//RXLaLfkK7z7b+4DtTmFwwnQbYjIUgnhd8EMb/EdT3yQGtS0pJkrZIXHkx6OECQQC4RsY8dPZbzLMVQjBbteU2B1rqj9G9oo3/6bCcrrbDRDDAKDvB7tpvvuVm056ImK7Tls86gZTTvQUkHnEz95L5AkEAiJ1QVFgwP+1WiQRqGQBlzVLanMAOYBwQ12WI+F0JpyYm/2vvhfuxPJTL+JclchG/JEKGH1DetiTZaVzPE8XI4QJAYvyBmBAsFfvLZuv7FgiLa45f5AgFXpxnMsTRPhkLy7Bhj5cNIouvgr9EcwvR6QY7QyWLg53/2A19KX3AyW1/4QJBAJ0IpOvSV4upJjoE+7stnD4F5nm9A1Ap4FM35VAwEWM1neICQvrfcbxYHb0HITS1lgf4pzVWlZlM7Sb3uEBrqO8=";
//        String data = "X99VBw9Dot6PHQQ2/xGIbRGi FCz4n0f2DnYkoUxY WIASg/hAmiytVGuYzjCY4u0 cz5NzetS5KHQO1BbSjE2d2l2q/ROEd9s6C9ffPbEktYEcu8l56PNGYM5zy3SGstUQqJQqKqKT8A563Vj2sZz14zAIyMpYuQBZN8dHhVDA=";
//        String newpub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaCGvCl02bONbNmCUAwidotpCu/dtBxwqdolQIQ48qaV1lc7csmZ+/ZEAHR5AxBndArc6T7iCaqRwpsf1nnjCeXzXq319GajLs15g5cu7u1CdyZ4ReYXXxhsbzu7rjwRfd35QhW6q725YswPkbaLJPyNbmnLLiuxYgE4AlYtVBfwIDAQAB";
//        String newpri = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMTLKPdA8bb5TyOFGbZl4JYP95mGXNWErCpXuIaDVNaVolWrf5Ic6wOaIoQjmBja95sF06/Y0mVR43qhXxEwKFFAub09KBgpB1EFeKMq9reElXhJQQhU/hL0HEWkzBhqLbf+/g2Q0Ov5nAl0WZ9pJxaC7Mzzuys5K5n51tL8wPfJAgMBAAECgYEAoYp3bUeVY3KRgQHQUoacbfJbeF3wNKF8xiFN6rVB3gfkwthNlv4/+Kv862Vgg/6yxEFrPG0KgHtQ9SgkJrLWVDDjx1kLVZrUpxojL+o0R38fF9YV76lY9CAmH3P2ODumCbN/DQAvKOGrVUHpcD8pfxmpCT7EzhWFZ+F0Igfv9GUCQQD/pDFmQFBxVyynj7zuqPRTNFFjBZxitm5tvcsLRJZOfC/vF8At+rj08GfL3Q0teEvTlys9BTvQa9eFbPo+Zii3AkEAxRHVWMxnyZuaqGjUgf9q9hMcUe9tdBtLdQXNwaHBg0X2BGihGBGAQtm1qsIHBXWcXRhf0mfZ/mkPaXa7EExjfwJBAO27sbzXPnfIi9xfjWGceWoPE0i05RPfd55Si8o0xFrEnocIXU3lBa+zCbPKJAEPB+UDhdA/V7qhL1IDYEw7EM0CQA1XkL8rT5eJrmXaexC2DzLLxm4RrLJgqrIoi26hme1eTjKsNsPfY3SjMrZ13m3TMZvn64zeNRcRW9bDEBUhZtcCQQCTvMMYOgoHKZbRY0dSm6Oq2EX8IfVX0gT5np6CT84AIxdFjztsO18zAIFsUoS1cpXBS0OJ4MeCwE4T31AaqHUg";
//        String newdata = "EkxDeTfTsd6qvd+QJwIenZMVGvlAQtYzL9yBbXAraclcXxUuy4eb1M5vsq6oXdTgOr0XUJy6hTJu02i/iIAPf6B4hEGrmGPKDoU0F5lDOeqSaa6vrUFnojvTvon9ujkLNBqs7fX5ebT8xyMGkPvqkcmRad7/xeUPdUAGOo3CRw8PlKTydVI4mfmuGMJOfAm1/QvyzYCU1+iP+2OuhTd+MHJ6zifUa5t64wA94BxMWrgkbBOt0zXKUZ6j1Rz8qr9JtYIPzmheqFi9lg0slhOQuaULD4yG5ZOrAZdncAf7rDS9izr4p096mfJOy8f3GmWBHAGRYo2UVfwJNPWe5gefSg==";
//        String d = decryptByPublicKey(data.replace(" ", "+"), pub);
////        System.out.println(d);
//        RSA.getKeys();
    }

    /**
     * 生成公钥和私钥
     *
     * @throws Exception
     */
    public static Map getKeys() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyStr = getPublicKeyStr(publicKey);
        String privateKeyStr = getPrivateKeyStr(privateKey);
        Map map = new HashMap();
        map.put("publickey", publicKeyStr);
        map.put("privatekey", privateKeyStr);

        return map;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 公钥指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 公钥加密   加解密X509
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String publickey) throws Exception {
        // 防止中文乱码
        String eData = URLEncoder.encode(data, "UTF-8");
        byte[] dataByte = eData.getBytes();
        byte[] keyBytes = Base64Utils.decode(publickey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        // Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = dataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64Utils.encode(encryptedData);
    }

    /**
     * 私钥解密 私钥加解密PKCS8
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String privatekey) throws Exception {

        byte[] encryptedData = Base64Utils.decode(data);
        byte[] keyBytes = Base64Utils.decode(privatekey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 防止中文乱码
        String dData = URLDecoder.decode(new String(decryptedData), "UTF-8");
        return dData;
    }

    /**
     * 公钥解密     加解密X509
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, String publickey) throws Exception {
        byte[] encryptedData = Base64Utils.decode(data);
        byte[] keyBytes = Base64Utils.decode(publickey);
        X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 防止中文乱码
        String dData = URLDecoder.decode(new String(decryptedData), "UTF-8");
        return dData;
    }

    /**
     * 私钥加密 私钥加解密PKCS8
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {
        // 防止中文乱码
        String eData = URLEncoder.encode(data, "UTF-8");
        byte[] dataByte = eData.getBytes();
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = dataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64Utils.encode(encryptedData);
    }

    /**
     * 获取模数和密钥
     *
     * @return
     */
    public static Map<String, String> getModulusAndKeys() {

        Map<String, String> map = new HashMap<String, String>();

        try {
            InputStream in = RSA.class
                    .getResourceAsStream("/rsa.properties");
            Properties prop = new Properties();
            prop.load(in);

            String modulus = prop.getProperty("modulus");
            String publicKey = prop.getProperty("publicKey");
            String privateKey = prop.getProperty("privateKey");

            in.close();

            map.put("modulus", modulus);
            map.put("publicKey", publicKey);
            map.put("privateKey", privateKey);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(privateKeyStr);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    public static String getPrivateKeyStr(PrivateKey privateKey)
            throws Exception {
        return new String(Base64Utils.encode(privateKey.getEncoded()));
    }

    public static String getPublicKeyStr(PublicKey publicKey) throws Exception {
        return new String(Base64Utils.encode(publicKey.getEncoded()));
    }


}