package yh.app.notification;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import org.androidpn.push.Constants;

public class RunStateHelper
{
	public static boolean isBackgroundRunning()
	{
		ActivityManager activityManager = (ActivityManager) Constants.App_Context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses)
		{
			if (appProcess.processName.equals(Constants.App_Context.getPackageName()))
			{
				if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
				{
					return true;
				} else
				{
					return false;
				}
			}
		}
		return false;
	}
}
