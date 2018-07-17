package com.yunhuakeji.app.utils;

public class IsNull
{
	/**
	 * 非空判断
	 * @param params
	 * @return
	 */
	public static boolean isNotNull(String... params)
	{
		try
		{
			for (int i = 0; i < params.length; i++)
			{
				if (params[i] == null || params[i].equals("") || params[i].equals("null"))
					return false;
			}
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
}
