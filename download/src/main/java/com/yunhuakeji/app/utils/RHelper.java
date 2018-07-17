package com.yunhuakeji.app.utils;

import java.lang.reflect.Field;

import com.yunhuakeji.app.R;

import android.content.Context;

public class RHelper
{
	public int getColor(Context context, String name)
	{
		try
		{
			R.color cls = (R.color) Class.forName(context.getPackageName() + ".R$color").newInstance();
			Field f = cls.getClass().getField(name);
			return f.getInt(f.getName());
		} catch (Exception e)
		{
			return R.color.button;
		}
	}

	public int getId(Context context, String name)
	{
		try
		{
			R.id cls = (R.id) Class.forName(context.getPackageName() + ".R$id").newInstance();
			Field f = cls.getClass().getField(name);
			return f.getInt(f.getName());
		} catch (Exception e)
		{
			return 0;
		}
	}

	public int getDrawable(Context context, String name)
	{
		try
		{
			R.drawable cls = (R.drawable) Class.forName(context.getPackageName() + ".R$drawable").newInstance();
			Field f = cls.getClass().getField(name);
			return f.getInt(f.getName());
		} catch (Exception e)
		{
			return 0;
		}
	}
}
