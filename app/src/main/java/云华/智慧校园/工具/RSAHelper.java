package 云华.智慧校园.工具;

import android.annotation.SuppressLint;
import android.util.Base64;

import com.example.app3.utils.RSAUtil;

import org.androidpn.push.Constants;
import org.json.JSONArray;

import java.io.ObjectInputStream;
import java.net.URLDecoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

@SuppressLint("TrulyRandom")
@SuppressWarnings(
        {
                "rawtypes", "deprecation"
        })
public class RSAHelper {
    /**
     * 指定加密算法为DESede
     */
    private String ALGORITHM = "RSA/ECB/NoPadding";

    public String encrypt(String source, int d) {
        try {
            return new String(Base64.encode(RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile()), source.getBytes()), Base64.DEFAULT));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 加密方法 source： 源数据
     */
    public String encrypt(String source) {
        try {
            /** 将文件中的公钥对象读出 */
            ObjectInputStream ois = new ObjectInputStream(Constants.App_Context.getAssets().open("Yunhua_Sdk.png"));
            Key key = (Key) ois.readObject();
            ois.close();
            /** 得到Cipher对象来实现对源数据的RSA加密 */
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] b = source.getBytes();
            /** 执行加密操作 */
            byte[] b1 = cipher.doFinal(b);
            return new String(Base64.encode(b1, Base64.NO_PADDING));
        } catch (Exception e) {
            return "";
        }
    }

    public String decode(String str) {
        try {
            String s = str.replace("\\n", " ").replace("\\", "").replace(" ", "\n").replace("[", "").replace("]", "").replace("\"", "");
//            Log.d("zsp", s);

//            str = "lcpZB1TFnA5pfkWxluTTM6wf5AA32IYAwbXG943jvJ79ABInOztQJ3cYKUROIUuvxWW9/x3hRaXy\nPS+2ZU3gjmvPrYATvMU0/PLzJyaQd0bnBfGklSjF8d2p9WM7TunmyvWH8bEkvBXHn4M/NK4dvLQR\nDEtHIYyxd74xIg0YT/g=\n";
            RSAUtil rsaUtil = new RSAUtil();
            return rsaUtil.decryptByKeyString(RSAEncrypt.getPrivateKey(), s);
//            return new String(RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile()), Base64.decode(str.getBytes("utf-8"), Base64.DEFAULT))).trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String decode(JSONArray jsa) {
        String result = "";
        for (int i = 0; i < jsa.length(); i++) {
            try {
                result += decode(jsa.get(i).toString());
            } catch (Exception e) {
            }
        }
        return result;
    }

    public List<String> encrypt(List<String> list) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
            temp.add(encrypt(list.get(i), 0));
        return temp;
    }

    /**
     * 解密算法 cryptograph:密文
     */
    public String decrypt(String cryptograph) throws Exception {
        /** 将文件中的私钥对象读出 */
        ObjectInputStream ois = new ObjectInputStream(Constants.App_Context.getAssets().open("Yunhua_Sdk_Resource.png"));
        Key key = (Key) ois.readObject();
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        System.out.println("cryptograph-length:" + cryptograph.getBytes().length);
        byte[] b1 = Base64.decode(cryptograph.getBytes(), Base64.DEFAULT);
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        ois.close();
        return URLDecoder.decode(new String(b).trim());
    }

    public String decrypt(List list) {
        String result = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    decrypt(list.get(i).toString());
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        return result;
    }

    public String decrypt(JSONArray jsArray) {
        String result = "";
        if (jsArray != null && jsArray.length() > 0) {
            for (int i = 0; i < jsArray.length(); i++) {
                try {
                    result += decrypt(jsArray.get(i).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static List<String> getDivLines(String inputString, int length) {

        List<String> divList = new ArrayList<String>();
        int remainder = (inputString.length()) % length;
        // 一共要分割成几段
        int number = (int) Math.floor((inputString.length()) / length);
        for (int index = 0; index < number; index++) {
            String childStr = inputString.substring(index * length, (index + 1) * length);
            divList.add(childStr);
        }
        if (remainder > 0) {
            String cStr = inputString.substring(number * length, inputString.length());
            divList.add(cStr);
        }
        return divList;
    }

}
