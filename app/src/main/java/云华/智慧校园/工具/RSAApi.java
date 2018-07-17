package 云华.智慧校园.工具;

import org.json.JSONArray;

import android.util.Base64;

public class RSAApi
{
	public static String getEncryptSecurity(String str)
	{
		return new JSONArray(new RSAHelper().encrypt(RSAHelper.getDivLines(str, 50))).toString();
	}

	public static String getDecryptSecurity(String str)
	{
		try
		{
			return new String(RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile()), Base64.decode(str.getBytes("utf-8"), Base64.DEFAULT)));
		} catch (Exception e)
		{
			return "";
		}
	}
}
