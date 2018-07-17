package yh.app.tool;

import org.androidpn.push.Constants;

import android.util.Base64;

import com.example.app4.tool.UserMessageTool;

import 云华.智慧校园.工具.RSAEncrypt;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具._链接地址导航;

public class Ticket
{
	static String t;
	public static String getFunctionTicket(String function_id)
	{
		try
		{
			String rsa = new SqliteHelper().rawQuery("select function_key from new_apply_function where function_id='" + function_id + "'").get(0).get("function_key");
			String ticket =new RSAHelper().decode(rsa);
			t=MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code + ticket.trim());
			return MD5.MD5(_链接地址导航.addString + UserMessageTool.getUserId() + UserMessageTool.getPassWordUnencrypted() + ticket.trim());
			// return Ticket_old.getFunctionTicket(function_id);
		} catch (Exception e)
		{
			return "";
		}
	}
	/**
	 * 生成票据
	 * @param jicheng_key 集成key
	 * @return
	 */
	public static String getFunctionTicket2(String jicheng_key)
	{
		try
		{
			String result = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code +new String(RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile()), Base64.decode(jicheng_key, Base64.DEFAULT))).trim());
			return result;// return Ticket_old.getFunctionTicket(function_id);
		} catch (Exception e)
		{
			return "";
		}
	}

	public static String getFunctionAction(String function_id)
	{
		return new SqliteHelper().rawQuery("select cls from button where FunctionID='" + function_id + "'").get(0).get("cls");
	}

	public static String getFunctionPKG(String function_id)
	{
		return new SqliteHelper().rawQuery("select pkg from button where FunctionID='" + function_id + "'").get(0).get("pkg");
	}

	public static String getFunctionName(String function_id)
	{
		try
		{
			return new SqliteHelper().rawQuery("select name from button where FunctionID='" + function_id + "'").get(0).get("name");
		} catch (Exception e)
		{
			return "";
		}
	}

	public static String getLoginTicket(String userid, String code)
	{
		return getTicket("yunhua", userid, code);
	}

	public static String getPushTicket(String userid, String code)
	{
		return getTicket("20161017152240318", userid, code);
	}

	public static String getTicket(String key, String userid, String code)
	{
		return MD5.MD5(String.format("%s%s%s", new Object[]
		{
				key, userid, code
		}));
	}
}
