package com.yunhuakeji.app.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTools
{
	public static JSONObject createJsonObject(String json)
	{
		try
		{
			return new JSONObject(json);
		} catch (Exception e)
		{
			return new JSONObject();
		}
	}

	public static JSONObject accumulate(JSONObject jso, String name, Object value)
	{
		try
		{
			jso.accumulate(name, value);
		} catch (Exception e)
		{
		}
		return jso;
	}

	public static JSONArray getJSONArray(JSONObject jso, String name)
	{
		try
		{
			return jso.getJSONArray(name);
		} catch (JSONException e)
		{
			return new JSONArray();
		}
	}

	public static String[] getString(JSONArray jsa, String... name)
	{
		String[] array = new String[name.length];
		for (int i = 0; i < name.length; i++)
		{
			try
			{
				array[i] = (jsa.getJSONObject(0).getString(name[i]) != null) ? jsa.getJSONObject(0).getString(name[i]) : "暂无数据";
			} catch (Exception e)
			{
				array[i] = "暂无数据";
			}
		}
		return array;
	}

	public static String[] getString(JSONObject jso, String... name)
	{
		try
		{
			String[] array = new String[name.length];
			for (int i = 0; i < name.length; i++)
			{	
				try
				{
					String s = jso.getString(name[i]);
					array[i] = (s != null && !s.equals("null")) ? jso.getString(name[i]) : "";
				} catch (Exception e)
				{
					array[i] = "";
				}
			}
			return array;
		} catch (Exception e)
		{
			return new String[name.length];
		}
	}

	public static String[] getString(JSONObject jso, String defaultString, String... name)
	{
		try
		{
			String[] array = new String[name.length];
			for (int i = 0; i < name.length; i++)
				try
				{
					String s = jso.getString(name[i]);
					array[i] = (s != null && !s.equals("null")) ? jso.getString(name[i]) : defaultString;
				} catch (Exception e)
				{
					array[i] = defaultString;
				}
			return array;
		} catch (Exception e)
		{
			return new String[name.length];
		}
	}

	public static JSONObject getJSONObject(JSONArray jsa, int index)
	{
		try
		{
			return jsa.getJSONObject(index);
		} catch (Exception e)
		{
			// TODO: handle exception
			return new JSONObject();
		}
	}

	public static List<Map<String, String>> getListMapBtJsonArray(JSONArray jsa)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try
		{
			for (int i = 0; i < jsa.length(); i++)
			{
				list.add(getMapBtJsonObject(jsa.getJSONObject(i)));
			}
		} catch (Exception e)
		{
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMapBtJsonObject(JSONObject jso)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Iterator<String> iteratorKeys = jso.keys();
			while (iteratorKeys.hasNext())
			{
				String name = iteratorKeys.next();
				map.put(name, jso.getString(name));
			}
		} catch (Exception e)
		{
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getKeysByJsonObject(JSONObject jsonObject)
	{
		List<String> keys = new ArrayList<String>();
		Iterator<String> iteratorKeys = jsonObject.keys();
		while (iteratorKeys.hasNext())
		{
			keys.add(iteratorKeys.next());
		}
		return keys;
	}

}