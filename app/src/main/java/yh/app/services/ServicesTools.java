package yh.app.services;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServicesTools
{
	public boolean isRunning(Context context, String serverName)
	{
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(100); // 30是最大值
		for (RunningServiceInfo info : infos)
		{
			if (info.service.getClassName().equals(serverName))
			{
				return true;
			}
		}
		return false;
	}
}
