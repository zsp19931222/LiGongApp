package 云华.智慧校园.工具;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public static Map<String, String> getMapToJson(JSONObject bundle)
    {
	Map<String, String> map = new HashMap<String, String>();
	if (bundle != null)
	{
	    Iterator<String> keys = bundle.keys();
	    while (keys.hasNext())
	    {
		String name = keys.next();
		map.put(name, JsonTools.getString(bundle, name));
	    }
	}
	return map;
    }

    public static List<Map<String, String>> getListMapToJson(JSONArray bundle)
    {
	List<Map<String, String>> list = new ArrayList<>();
	for (int i = 0; bundle != null && i < bundle.length(); i++)
	{
	    JSONObject jso = JsonTools.getJSONObject(bundle, i);
	    list.add(getMapToJson(jso));
	}
	return list;
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

}
