package com.example.app4.util;

import java.util.List;
import java.util.Set;

public class IsNull
{
	public static boolean isNotNull(Object object)
	{
		if (object == null)
		{
			return false;
		}
		if ((object instanceof List))
		{
			return isNotNull((List<?>) object);
		}
		if ((object instanceof Set))
		{
			return isNotNull((Set<?>) object);
		}
		if (object instanceof String[])
		{
			return isNotNull((String[]) object);
		}
		if (object instanceof String)
		{
			return isNotNull((String) object);
		}
		return object != null;
	}

	public static boolean isNotNull(List<?> list)
	{
		return (list != null) && (list.size() > 0);
	}

	public static boolean isNotNull(String param)
	{
		return param != null && !param.isEmpty();
	}

	public static boolean isNotNull(String... params)
	{
		if (params == null)
		{
			return false;
		}
		for (int i = 0; i < params.length; i++)
		{
			if (params[i] == null || params[i].isEmpty())
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotNull(Set<?> params)
	{
		return (params != null) && (params.size() > 0);
	}
}