package 云华.智慧校园.工具;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.annotation.SuppressLint;
import android.util.Base64;

public class RSAEncrypt {
    private static String privateKey =
            "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALUPuqBiS+1YbK/R" +
                    "cm+cVkpQwl9SXNBTGvxhi8kWBIL9AFlouREZsF+WfG0Ii796kTiOXxaVEa0zLjFM" +
                    "uAn/jobRZckEhE9zJAOeSyAvU8qQGFYIKjuHR9RxHCzrecVEDWDUbALlGJxZdp7a" +
                    "0/hojzVZfnbfoWgm4khcrYQa76WVAgMBAAECgYEAmc0wpMia4pR4TqlF4hUVH6+W" +
                    "TM5z1OqjQ7vAuCGh13r+bvSMMEB4F9qG+z+FJjQBY99cWpxqFYwiMvKOar/Q2ro4" +
                    "ge+EdcisU0k5WXXubUpol743H/KJpnd8kCWFk9pNiLuOl7qiJlTkB5KivvX2JxDt" +
                    "mvRO02oFfLXgAX+y00ECQQDl/iiVb6fLJhrf9FDbzvk3qRB7eruApkGr0DYS+TDy" +
                    "q72GSnkHEfj5EuB23NaeChyCJJtH09euSaIHw/chnKlpAkEAyYkabsZ+CyUPquJo" +
                    "p1UD2hFStPI+DDvniz19YTxzUcU/Fs38kTsoew82IhEm/k2rvaRkKRJLknHvm4h/" +
                    "PnwJTQJBALebKgz6YSrFlcjaAz8nQT+VIUpiVZPDpkOiabjF5LSmNBwkEfB6AZfd" +
                    "4QIjFNZ/3fhrfuddkB5cPBUU9ZKIvZkCQQCR3cPd7ZiI5HgkjN6GTkgNa4BbKwGx" +
                    "xSHfa8/1stUcmBEDpm9phlHUT7w0iAmbAgiNqBA+kdlU01ZDUlYWmZv5AkAhtORz" +
                    "WYq16KyHfM+7kgxniufX7dBcIWh4MOStl1q5OALrfsenzrf8Yzl5ss9JmmXZFuq2" +
                    "Am0fy9Sv6VjG7WrE";
    private static String publicKey =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDACwPDxYycdCiNeblZa9LjvDzb" +
                    "iZU1vc9gKRcG/pGjZ/DJkI4HmoUE2r/o6SfB5az3s+H5JDzmOMVQ63hD7LZQGR4k" +
                    "3iYWnCg3UpQZkZEtFtXBXsQHjKVJqCiEtK+gtxz4WnriDjf+e/CxJ7OD03e7sy5N" +
                    "Y/akVmYNtghKZzz6jwIDAQAB";

    private static final char[] HEX_CHAR =
            {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
            };

    @SuppressLint("TrulyRandom")
    public static void genKeyPair(String filePath) {
        // KeyPairGenerator��������ɹ�Կ��˽Կ�ԣ�����RSA�㷨��ɶ���
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // ��ʼ����Կ�����������Կ��СΪ96-1024λ
        keyPairGen.initialize(1024, new SecureRandom());
        // ���һ����Կ�ԣ�������keyPair��
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // �õ�˽Կ
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // �õ���Կ
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        try {
            // �õ���Կ�ַ�
            String publicKeyString = new String(Base64.encode(publicKey.getEncoded(), Base64.DEFAULT));
            // �õ�˽Կ�ַ�
            String privateKeyString = new String(Base64.encode(privateKey.getEncoded(), Base64.DEFAULT));
            // ����Կ��д�뵽�ļ�
            FileWriter pubfw = new FileWriter(filePath + RSAEncrypt.publicKey);
            FileWriter prifw = new FileWriter(filePath + RSAEncrypt.privateKey);
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadPublicKeyByFile() {
        return publicKey;
    }

    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴��㷨");
        } catch (InvalidKeySpecException e) {
            throw new Exception("��Կ�Ƿ�");
        } catch (NullPointerException e) {
            throw new Exception("��Կ���Ϊ��");
        }
    }

    /**
     * ���ļ��м���˽Կ
     * <p>
     * ˽Կ�ļ���
     *
     * @return �Ƿ�ɹ�
     * @throws Exception
     */
    public static String loadPrivateKeyByFile() throws Exception {
//		try
//		{
//			return readStream(Constants.App_Context.getAssets().open(privateKey));
        return privateKey;
//		} catch (IOException e)
//		{
//			throw new Exception("˽Կ��ݶ�ȡ����");
//		} catch (NullPointerException e)
//		{
//			throw new Exception("˽Կ������Ϊ��");
//		}
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴��㷨");
        } catch (InvalidKeySpecException e) {
            throw new Exception("˽Կ�Ƿ�");
        } catch (NullPointerException e) {
            throw new Exception("˽Կ���Ϊ��");
        }
    }

    /**
     * ��Կ���ܹ��
     *
     * @param publicKey     ��Կ
     * @param plainTextData �������
     * @return
     * @throws Exception ���ܹ���е��쳣��Ϣ
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("���ܹ�ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˼����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("���ܹ�Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            throw new Exception("�����������");
        }
    }

    /**
     * ˽Կ���ܹ��
     *
     * @param privateKey    ˽Կ
     * @param plainTextData �������
     * @return
     * @throws Exception ���ܹ���е��쳣��Ϣ
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
        if (privateKey == null) {
            throw new Exception("����˽ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˼����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("����˽Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            throw new Exception("�����������");
        }
    }

    /**
     * ˽Կ���ܹ��
     *
     * @param privateKey ˽Կ
     * @param cipherData �������
     * @return ����
     * @throws Exception ���ܹ���е��쳣��Ϣ
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("����˽ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˽����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("����˽Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            throw new Exception("�����������");
        }
    }

    /**
     * ��Կ���ܹ��
     *
     * @param publicKey  ��Կ
     * @param cipherData �������
     * @return ����
     * @throws Exception ���ܹ���е��쳣��Ϣ
     */
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
        if (publicKey == null) {
            throw new Exception("���ܹ�ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˽����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("���ܹ�Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            throw new Exception("�����������");
        }
    }

    /**
     * �ֽ����תʮ������ַ�
     *
     * @param data �������
     * @return ʮ���������
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // ȡ���ֽڵĸ���λ ��Ϊ����õ���Ӧ��ʮ����Ʊ�ʶ�� ע���޷������
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            // ȡ���ֽڵĵ���λ ��Ϊ����õ���Ӧ��ʮ����Ʊ�ʶ��
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }

    @SuppressWarnings("unused")
    private static String readStream(InputStream in) {
        try {
            byte[] b = new byte[in.available()];
            in.read(b);
            return new String(b);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取解密key
     *
     * @return
     */

    public static String getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取加密key
     *
     * @return
     */
    public static String getPublicKey() {
        return publicKey;
    }
}