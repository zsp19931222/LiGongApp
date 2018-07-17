package yh.app.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

public class JsonListMap
{
	public List<Map<String, String>> JsonToMap(JSONArray jsa, String defaultString, String... parm)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < jsa.length(); i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < parm.length; j++)
			{
				try
				{
					map.put(parm[j], jsa.getJSONObject(i).getString(parm[j]));
				} catch (JSONException e)
				{
					map.put(parm[j], defaultString);
				}
			}
			list.add(map);
		}
		return list;

	}

	public String MapToJson(List<Map<String, Object>> tjlist)
	{
		String s = tjlist.toString();
		s = s.replace("{", "{\"");
		s = s.replace("}", "\"}");
		s = s.replace("=", "\":\"");
		s = s.replace(", ", "\",\"");
		s = s.replace("}\"", "}");
		s = s.replace("\"{", "{");
		s = s.replace("{\"\"},", "");
		return s;
	}
	public String MapToJsonString(List<Map<String, String>> tjlist)
	{
		String s = tjlist.toString();
		s = s.replace("{", "{\"");
		s = s.replace("}", "\"}");
		s = s.replace("=", "\":\"");
		s = s.replace(", ", "\",\"");
		s = s.replace("}\"", "}");
		s = s.replace("\"{", "{");
		s = s.replace("{\"\"},", "");
		return s;
	}
}
