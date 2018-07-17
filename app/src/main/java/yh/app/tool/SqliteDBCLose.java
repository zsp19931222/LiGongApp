package yh.app.tool;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SqliteDBCLose
{
	private SQLiteDatabase db;
	private Cursor c;

	public SqliteDBCLose(SQLiteDatabase db, Cursor c)
	{
		this.c = c;
		this.db = db;
	}

	public void close()
	{
		try
		{
			if (c != null)
				c.close();
			if (db != null && db.isOpen())
				db.close();
		} catch (SQLException e)
		{
		} catch (Exception e)
		{
		}
	}
}
