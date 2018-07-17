package 云华.智慧校园.工具;

public class NetResultHelper
{
	public static String dealHJJResult(String result)
	{
		return result.substring(1, result.length() - 1).replace("\\", "");
	}
}