package yh.app.activitytool;

import android.app.ActivityManager;
import android.content.Context;
import org.androidpn.push.Constants;

public class BreakApp
{
	@SuppressWarnings("deprecation")
	public static void closeAPP()
	{
		ActivityManager am = (ActivityManager) Constants.App_Context.getSystemService(Context.ACTIVITY_SERVICE);
		am.restartPackage(Constants.App_Context.getPackageName());
	}
}
