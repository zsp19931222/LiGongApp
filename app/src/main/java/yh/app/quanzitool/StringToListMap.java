package yh.app.quanzitool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

public class StringToListMap
{
	private List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> toListMap(String str)
	{
		try
		{
			JSONArray jsa = new JSONArray(str);
			for (int i = 0; i < jsa.length(); i++)
			{
				try
				{
					Map<String, String> map = new HashMap<String, String>();
					map.put("FRIEND_ID", jsa.getJSONObject(i).getString("FRIEND_ID"));
					map.put("NAME", jsa.getJSONObject(i).getString("NAME"));
					maplist.add(map);
				} catch (Exception e)
				{
				}
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return maplist;
	}
}
