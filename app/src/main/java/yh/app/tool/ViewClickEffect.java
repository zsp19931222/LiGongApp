package yh.app.tool;

import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;

public class ViewClickEffect
{
	public static void doEffect(View v, int time, Context context, String cls, String pkg, String[][] array)
	{
		try
		{
			v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		} catch (Exception e)
		{
		}
		new timer(v, time, context, cls, pkg, array).start();
	}

	public static void doEffect(View v, int time, Context context, String cls, String pkg, Map<String, String> extra)
	{
		try
		{
			v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		} catch (Exception e)
		{
		}
		new timer(v, time, context, cls, pkg, extra).start();
	}
}
