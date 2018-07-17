package com.yunhuakeji.app.utils;

import java.util.Properties;

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
