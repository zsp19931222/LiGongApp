package 云华.智慧校园.工具;

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
    /**
     * 筛选出正确的数据，剔除异常数据
     * 
     * @param jsa
     * @param name
     * @return
     */
    public static JSONArray getJSONArray(JSONArray jsa, String... name)
    {
	JSONArray temp = new JSONArray();
	for (int i = 0; jsa != null && i < jsa.length(); i++)
	{
	    try
	    {
		JSONObject jso = jsa.getJSONObject(i);
		if (hasName(jso, name))
		{
		    temp.put(jso);
		}
	    } catch (Exception e)
	    {
	    }
	}
	return temp;
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

    public static String getString(JSONObject jso, String name)
    {
	try
	{
	    return jso.getString(name);
	} catch (Exception e)
	{
	    return "";
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

    public static boolean hasName(JSONObject jso, String... name)
    {
	if (jso == null && name == null)
	{
	    return false;
	}
	for (int i = 0; i < name.length; i++)
	{
	    if (!jso.has(name[i]))
	    {
		return false;
	    }
	}
	return true;
    }
}