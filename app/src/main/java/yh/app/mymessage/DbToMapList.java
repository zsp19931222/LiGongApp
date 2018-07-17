package yh.app.mymessage;

import java.util.ArrayList;

import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbToMapList
{
	public DbToMapList()
	{
	}

	public List<Map<String, String>> doit(String function_id)
	{
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		SQLiteDatabase db = new SqliteHelper().getRead();
		Cursor c = db.rawQuery("select tzggid,message,fssj,isread,type,url,func_id ,bjzd ,fqbm from tzgg where userid=? and func_id=? order by fssj Desc ", new String[]
			{ Constants.number, function_id });
		while (c.moveToNext())
		{
			Map<String, String> map = new HashMap<String, String>();
			String s = c.getString(1);
			map.put("id", c.getString(0));
			map.put("title", s.split("::::")[0]);
			map.put("text", s.split("::::")[1]);
			map.put("date", c.getString(2));
			map.put("isread", c.getString(3));
			map.put("delete", "false");
			map.put("type", c.getString(4));
			map.put("url", c.getString(5));
			map.put("func_id", c.getString(6));
			map.put("bjzd", c.getString(7));
			map.put("fqbm", c.getString(8));
			mapList.add(map);
		}
		c.close();
		db.close();
		for (int i = 0; i < 10; i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "1");
			map.put("title", "习近平考察金寨");
			map.put("text", ToDBC("新华网北京4月25日电　4月24日上午11时30分许，习近平总书记的身影出现在安徽省六安市金寨县的红军广场。金寨，地处大别山腹地，被誉为“红军的摇篮、将军的故乡”。这里是红四方面军、红25军的主要发源地，先后组建过11支主力红军队伍，革命战争年代，10万金寨儿女为国捐躯。今年是红军长征胜利80周年。"));
			map.put("date", "2015-05-12");
			map.put("isread", "1");
			map.put("delete", "false");
			map.put("type", "101");
			map.put("url", "1");
			map.put("func_id", "1");
			map.put("bjzd", "1");
			map.put("fqbm", "1");
			mapList.add(map);
		}
		return mapList;
	}

	public static String ToDBC(String input)
	{
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == 12288)
			{
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
}
