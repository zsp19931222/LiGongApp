package yh.app.IntentTools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentTools
{
	public static void intent(Context context, String action, String packageName, boolean isFinish, boolean isClearTop, String Extras)
	{
		try
		{
			Intent intent = new Intent();
			intent.setAction(action);
			intent.setPackage(packageName);
			if (Extras != null)
				intent.putExtra("Extras", Extras);
			if (isClearTop)
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			((Activity) context).startActivity(intent);
			if (isFinish)
				((Activity) context).finish();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
