package com.example.app3.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtil {
    private static Charset charset = Charset.forName("utf-8");
    private Charset tempCharset = null;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public RSAUtil() {
    }

    public RSAUtil(Charset charset) {
        this.tempCharset = charset;
    }

    public Charset getCharset() {
        return tempCharset == null ? charset : tempCharset;
    }

    public String decryptByFile(String path, String fileName, String dataString) throws Exception {
        String keyString = loadKeyByFile(path, fileName);

        return this.decryptByKeyString(keyString, dataString);
    }

    public String decryptByKeyString(String keyString, String dataString) throws Exception {
        RSAPrivateKey key = loadPrivateKeyByStr(keyString);
        byte[] dataByte = UtilBase64.decode(dataString, 0);
        return new String(this.doRSA(key, 2, dataByte), getCharset());
    }
    public String decryptByKeyString1(String keyString, String dataString) throws Exception {
        RSAPrivateKey key = loadPrivateKeyByStr(keyString);
        byte[] dataByte = UtilBase64.decode(dataString, 0);
        return new String(this.doRSA1(key, 2, dataByte), getCharset());
    }

    public String encryptByFile(String path, String fileName, String dataString) throws Exception {
        String keyString = loadKeyByFile(path, fileName);
        return this.encryptByKeyString(keyString, dataString);
    }

    public String encryptByKeyString(String keyString, String dataString) throws Exception {
        RSAPublicKey key = this.loadRsaPublicKey(keyString);
        return new String(UtilBase64.encode(this.doRSA(key, 1, dataString.getBytes(getCharset())), 0));
    }

    private byte[] doRSA(Key key, int RSAMode, byte[] dataByte) throws Exception {
        if (key == null) {
            throw new Exception("加密公钥为空, 请设置");
        } else {
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("RSA/ECB/NoPadding", "BC");
                cipher.init(RSAMode, key);
                byte[] output = cipher.doFinal(dataByte);
                return output;
            } catch (NoSuchAlgorithmException var6) {
                throw new Exception("无此加密算法");
            } catch (NoSuchPaddingException var7) {
                var7.printStackTrace();
                return null;
            } catch (InvalidKeyException var8) {
                throw new Exception("加密公钥非法,请检查");
            } catch (IllegalBlockSizeException var9) {
                throw new Exception("明文长度非法");
            } catch (BadPaddingException var10) {
                throw new Exception("明文数据已损坏");
            }
        }
    }
    private byte[] doRSA1(Key key, int RSAMode, byte[] dataByte) throws Exception {
        if (key == null) {
            throw new Exception("加密公钥为空, 请设置");
        } else {
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
                cipher.init(RSAMode, key);
                byte[] output = cipher.doFinal(dataByte);
                return output;
            } catch (NoSuchAlgorithmException var6) {
                throw new Exception("无此加密算法");
            } catch (NoSuchPaddingException var7) {
                var7.printStackTrace();
                return null;
            } catch (InvalidKeyException var8) {
                throw new Exception("加密公钥非法,请检查");
            } catch (IllegalBlockSizeException var9) {
                throw new Exception("明文长度非法");
            } catch (BadPaddingException var10) {
                throw new Exception("明文数据已损坏");
            }
        }
    }

    private RSAPublicKey loadRsaPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = UtilBase64.decode(publicKeyStr, 0);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException var5) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException var6) {
            throw new Exception("公钥非法");
        } catch (NullPointerException var7) {
            throw new Exception("公钥数据为空");
        } catch (Exception var8) {
            throw new Exception("未知错误");
        }
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = UtilBase64.decode(privateKeyStr, 0);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException var4) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException var5) {
            throw new Exception("私钥非法");
        } catch (NullPointerException var6) {
            throw new Exception("私钥数据为空");
        } catch (Exception var7) {
            throw new Exception("未知错误");
        }
    }

    public static String loadKeyByFile(String path, String fileName) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path, fileName)));
            String readLine = null;
            StringBuilder sb = new StringBuilder();

            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }

            br.close();
            return sb.toString();
        } catch (IOException var5) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException var6) {
            throw new Exception("公钥输入流为空");
        } catch (Exception var7) {
            throw new Exception("未知错误");
        }
    }
}

