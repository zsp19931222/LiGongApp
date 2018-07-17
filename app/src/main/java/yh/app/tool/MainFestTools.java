package yh.app.tool;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class MainFestTools
{
	public String getData(Context context,String name)
	{
		ActivityInfo info;
		try
		{
			info = context.getPackageManager().getActivityInfo(((Activity) context).getComponentName(), PackageManager.GET_META_DATA);
			return info.metaData.getString(name);
		} catch (NameNotFoundException e)
		{
			return null;
		}

	}
}