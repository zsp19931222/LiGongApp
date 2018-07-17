package yh.app.tool;

import org.androidpn.push.Constants;
import 云华.智慧校园.工具._链接地址导航;

public class Ticket_old
{

	public static String getFunctionTicket(String function_id)
	{
		return MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code + new SqliteHelper().rawQuery("select key from button where FunctionID='" + function_id + "'").get(0).get("key"));
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
		return MD5.MD5(String.format("yunhua%s%s", new Object[]
		{
				userid, code
		}));
	}
}
