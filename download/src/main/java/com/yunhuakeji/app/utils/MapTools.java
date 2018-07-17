package com.yunhuakeji.app.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.os.Bundle;

public class MapTools
{
	/**
	 * @param array
	 *            二维数组，大小为2
	 * @return
	 */
	public static Map<String, String> buildMap(String[][] array)
	{
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < array.length; i++)
		{
			try
			{
				map.put(array[i][0], array[i][1]);
			} catch (Exception e)
			{
			}
		}
		return map;
	}
	
	/**
	 * @param array
	 *            二维数组，大小为2
	 * @return
	 */
	public static Map<Object, Object> buildMap(Object[][] array)
	{
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < array.length; i++)
		{
			try
			{
				map.put(array[i][0], array[i][1]);
			} catch (Exception e)
			{
			}
		}
		return map;
	}

	public static Map<String, String> CopyMap(Map<String, String> map)
	{
		Map<String, String> temp = new HashMap<String, String>();
		Iterator<String> keys = getMapKeys(map);
		while (keys.hasNext())
		{
			String key = keys.next();
			temp.put(key, map.get(key));
		}
		return temp;
	}

	public static Map<String, String> getMapToBundle(Bundle bundle)
	{
		Map<String, String> map = new HashMap<String, String>();
		String[] keys = BundleTools.getBundleKeys(bundle);
		for (int i = 0; i < keys.length; i++)
		{
			map.put(keys[i], bundle.getString(keys[i]));
		}
		return map;
	}

	public static Map<String, String> CopyMapToLowerCase(Map<String, String> map)
	{
		Map<String, String> temp = new HashMap<String, String>();
		Iterator<String> keys = getMapKeys(map);
		while (keys.hasNext())
		{
			String key = keys.next();
			temp.put(key.toLowerCase(Locale.getDefault()), map.get(key));
		}
		return temp;
	}

	public static Iterator<String> getMapKeys(Map<String, String> map)
	{
		return map.keySet().iterator();
	}
	
	public static Iterator<String> getMapKeysByObject(Map<String, Object> map)
	{
		return map.keySet().iterator();
	}

}
