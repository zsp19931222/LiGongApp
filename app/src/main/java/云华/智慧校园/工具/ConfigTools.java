package 云华.智慧校园.工具;

import java.util.Properties;

import org.androidpn.push.Constants;

public class ConfigTools
{
	public static String loadProperties(String name)
	{
		Properties props = new Properties();
		try
		{
			int id = Constants.App_Context.getResources().getIdentifier("androidpn", "raw", Constants.App_Context.getPackageName());
			props.load(Constants.App_Context.getResources().openRawResource(id));
			return props.getProperty(name, "");
		} catch (Exception e)
		{
			return "";
		}
	}
}