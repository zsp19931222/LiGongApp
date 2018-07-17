package com.yunhuakeji.app.utils;

import java.lang.reflect.Field;

import com.yunhuakeji.app.R;

@SuppressWarnings("rawtypes")
public class ROutHelper
{
	public int getColor(Class context, String name)
	{
		try
		{
			R.color cls = (R.color) Class.forName(context.getName() + "$color").newInstance();
			Field f = cls.getClass().getField(name);
			return f.getInt(f.getName());
		} catch (Exception e)
		{
			return R.color.button;
		}
	}

	public int getId(Class context, String name)
	{
		try
		{
			R.id cls = (R.id) Class.forName(context.getName() + ".R$id").newInstance();
			Field f = cls.getClass().getField(name);
			return f.getInt(f.getName());
		} catch (Exception e)
		{
			return 0;
		}
	}

	public int getDrawable(Class context, String name)
	{
		try
		{
			R.drawable cls = (R.drawable) Class.forName(context.getName() + "$drawable").newInstance();
			Field f = cls.getClass().getField(name);
			return f.getInt(f.getName());
		} catch (Exception e)
		{
			return 0;
		}
	}
}
