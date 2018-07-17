package yh.app.db;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;

public class DbHelper
{
	SQLiteDatabase db;

	public DbHelper(SQLiteDatabase db)
	{
		this.db = db;
	}

	public void zjzd(String table, String zdm, String type)
	{
		try
		{
			db.execSQL("alter table " + table + " add " + zdm + " " + type);
		} catch (Exception e)
		{
		}

	}

	public void us(String table, String zdm, String values, List<Map<String, String>> listmap)
	{
		try
		{
			String sql = "update " + table + " set " + zdm + "='" + values + "'";
			if (listmap != null)
				for (int i = 0; (i < listmap.size()) || listmap != null; i++)
				{
					Map<String, String> map = listmap.get(i);
					if (i == 0)
					{
						sql = sql + " where " + map.get("zdm") + "='" + map.get("value") + "'";
					} else
						sql = sql + " and " + map.get("zdm") + "='" + map.get("value") + "'";
				}
			db.execSQL(sql);
		} catch (Exception e)
		{
		}

	}
}
