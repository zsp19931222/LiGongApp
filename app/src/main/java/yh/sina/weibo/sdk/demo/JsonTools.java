package yh.sina.weibo.sdk.demo;

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
	public static String[] getString(JSONArray jsa, String... name)
	{
		String[] array = new String[name.length];
		for (int i = 0; i < name.length; i++)
			try
			{
				array[i] = (jsa.getJSONObject(0).getString(name[i]) != null) ? jsa.getJSONObject(0).getString(name[i]) : "暂无数据";
			} catch (JSONException e)
			{
				array[i] = "暂无数据";
			} catch (Exception e)
			{
				array[i] = "暂无数据";
			}
		return array;
	}

	public static String[] getString(JSONObject jso, String... name)
	{
		try
		{
			String[] array = new String[name.length];
			for (int i = 0; i < name.length; i++)
				try
				{
					String s = jso.getString(name[i]);
					array[i] = (s != null && !s.equals("null")) ? jso.getString(name[i]) : "";
				} catch (JSONException e)
				{
					array[i] = "";
				} catch (Exception e)
				{
					array[i] = "";
				}
			return array;
		} catch (Exception e)
		{
			return null;
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
		} catch (JSONException e)
		{
		} catch (Exception e)
		{
		}
		return list;
	}

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