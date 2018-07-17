package yh.app.utils;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class FunctionHelper
{
	public void getFunctionList(Map<String, String> parames, final Handler handler)
	{
		new ThreadPoolManage().addPostTask(_链接地址导航.UIA.功能列表.getUrl(), parames, new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				int what = 0;
				try
				{
					what = dealData(new JSONObject(msg.getData().getString("msg")));
				} catch (Exception e)
				{
					what = 2;
				}
				new HandlerHelper().sendWhat(handler, what);
			}
		});
	}

	private int dealData(JSONObject array)
	{
		try
		{
			List<String> keys = JsonTools.getKeysByJsonObject(array);
			new SqliteHelper().execSQL("delete from button");
			for (int i = 0; i < array.length(); i++)
			{
				try
				{
					new SqliteHelper().execSQL("insert into button(FunctionID , name ,type ,cls ,pkg , key  ,face ,px ,function_tybj ) values(?,?,?,?,?,?,?,?,?)", new Object[]
					{
							array.getJSONObject(keys.get(i)).getString("function_id".toUpperCase(Locale.CHINA)), 
							array.getJSONObject(keys.get(i)).getString("function_name".toUpperCase(Locale.CHINA)), 
							array.getJSONObject(keys.get(i)).getString("function_type".toUpperCase(Locale.CHINA)), 
							array.getJSONObject(keys.get(i)).getString("class_name".toUpperCase(Locale.CHINA)), 
							array.getJSONObject(keys.get(i)).getString("package_name".toUpperCase(Locale.CHINA)), 
							array.getJSONObject(keys.get(i)).getString("INTEGRATE_KEY".toUpperCase(Locale.CHINA)), 
							array.getJSONObject(keys.get(i)).getString("function_face".toUpperCase(Locale.CHINA)), 
							Integer.valueOf(array.getJSONObject(keys.get(i)).getString("px".toUpperCase(Locale.CHINA))), 
							array.getJSONObject(keys.get(i)).getString("function_tybj".toUpperCase(Locale.CHINA))
					});
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return 1;
		} catch (Exception e)
		{
		}
		return 0;
	}
}
