package yh.app.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MyOpenHelper extends SQLiteOpenHelper
{

	// 调用父类构造器
	public MyOpenHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	// 重写onCreate方法
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE if not exists contacts(id varchar primary key,tell_sj varchar,tell_zj varchar,department_name varchar,department_pid varchar,isleaf varchar);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// 重写onUpgrade方法
	}

}