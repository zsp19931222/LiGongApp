package 云华.智慧校园.工具;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityHelper
{
	public void goHomeActivity(Context context)
	{
//		Intent intent = new Intent();
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.setAction("com.example.app3.HomePageActivity");
//		intent.setPackage(context.getPackageName());
//		context.startActivity(intent);
		((Activity) context).finish();
	}
}
