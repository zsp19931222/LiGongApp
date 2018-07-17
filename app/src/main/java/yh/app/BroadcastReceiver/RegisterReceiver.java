package yh.app.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class RegisterReceiver
{
	private static MyBroadcastReceiver mHomeKeyReceiver = null;

	public static void registerHomeKeyReceiver(Context context)
	{
		mHomeKeyReceiver = new MyBroadcastReceiver();
		final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

		context.registerReceiver(mHomeKeyReceiver, homeFilter);
	}

	public static void unregisterHomeKeyReceiver(Context context)
	{
		if (null != mHomeKeyReceiver)
		{
			context.unregisterReceiver(mHomeKeyReceiver);
		}
	}
}
