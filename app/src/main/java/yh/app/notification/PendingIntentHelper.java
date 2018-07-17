package yh.app.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class PendingIntentHelper
{
	private static int requestCode = 0;

	public PendingIntent buildPendingIntent(Context context, String action, String packageName)
	{
		Intent intent = new Intent();
		intent.setAction(action);
		intent.setPackage(packageName);
		requestCode++;
		PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}
}
