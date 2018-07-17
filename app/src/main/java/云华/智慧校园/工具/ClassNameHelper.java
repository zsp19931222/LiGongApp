package 云华.智慧校园.工具;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

@SuppressWarnings("deprecation")
public class ClassNameHelper
{

	public static String getCurrentClassName(Activity activity)
	{
		return ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1).get(0).topActivity.getClassName();
	}
}
