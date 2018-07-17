//package yh.app.tool;
//import java.util.List;
//import java.util.Map;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//public class JsonTool
//{
//	public static JSONObject getJsonOBObject(String str)
//	{
//		String json = str.replace("\\", "");
//		String jsonstr = json.substring(1, json.length() - 2);
//		if (!jsonstr.isEmpty())
//		{
//			try
//			{
//				return JSONObject.fromObject(jsonstr);
//			} catch (Exception e)
//			{
//				return new JSONObject();
//			}
//		}
//		else
//			return new JSONObject();
//	}
//	public static JSONArray getJsonArray(String str)
//	{
//		String json = str.replace("\\", "");
//		String jsonstr = json.substring(1, json.length() - 2);
//		if (!jsonstr.isEmpty())
//		{
//			try
//			{
//				return JSONArray.fromObject(jsonstr);
//			} catch (Exception e)
//			{
//				return new JSONArray();
//			}
//		}
//		else
//			return new JSONArray();
//	}
//	public static String toJson(Object o)
//	{
//		JSONObject json;
//		json = JSONObject.fromObject(o);
//		return json.toString();
//	}
//	public static String toJson(Object o, boolean isPrint)
//	{
//		JSONObject json;
//		json = JSONObject.fromObject(o);
//		String result = json.toString();
//		if (isPrint)
//		{
//			System.out.println(result);
//		}
//		return result;
//	}
//	public static String toJsonList(List<?> list)
//	{
//		JSONArray jsonArray = JSONArray.fromObject(list);
//		return jsonArray.toString();
//	}
//	public static String toJsonList(List<?> list, boolean isPrint)
//	{
//		JSONArray jsonArray = JSONArray.fromObject(list);
//		String result = jsonArray.toString();
//		if (isPrint)
//			System.out.println(result);
//		return result;
//	}
//	public static String toJson(Object o, Map<String, Object> prop, boolean isPrint)
//	{
//		JSONObject json = JSONObject.fromObject(o);
//		json.accumulateAll(prop);
//		String result = json.toString();
//		if (isPrint)
//		{
//			System.out.println(result);
//		}
//		return result;
//	}
//	public static <E> E[] toBeanList(String json, Class<E> clazz)
//	{
//		JSONArray array = JSONArray.fromObject(json);
//		// Product[] products=(Product[])
//		// JSONArray.toArray(array,Product.class);
//		@SuppressWarnings("unchecked")
//		E[] beans = (E[]) JSONArray.toArray(array, clazz);
//		return beans;
//	}
//	public static <E> E toBean(String json, Class<E> clazz)
//	{
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		// Product[] products=(Product[])
//		// JSONArray.toArray(array,Product.class);
//		@SuppressWarnings("unchecked")
//		E bean = (E) JSONObject.toBean(jsonObject, clazz);
//		return bean;
//	}
//}
