package yh.app.tool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 包	名:yh.app.tool
 * 类	名:JsonHelper.java
 * 功	能:对JSON数据进行判断,为null则返回"-"
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class JsonHelper {

	private  JSONObject json;
	public JsonHelper(JSONObject js)
	{
		json=js;
	}

	public String GetValue(String key)
	{
		String s= "-";;
		if (json.isNull(key)) {  
			return "-";
		} 
		else
		{
			try {
				s=json.getString(key);
				return s;
			} catch (JSONException e) {
				return s;
			}
		}
	}
	public String GetDate(String key)
	{
		String s= "-";
		if (json.isNull("userinfo")) {  
			return "-";
		} 
		else
		{
			try {
				s=json.getJSONObject("userinfo").getString(key);
				return s;
			} catch (JSONException e) {
				return s;
			}
		}
	}
}
