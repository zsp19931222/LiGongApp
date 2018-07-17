package yh.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author QueHailin
 */
public class NetworkService extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
//		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//		if (!NetWork.isNetworkAvailable(Constants.App_Context))
//		{
//			dealNetwork.isNotNetWork();
//			return;
//		}
//		if (!wifiNetInfo.isAvailable())
//		{
//			dealNetwork.isNotWifi();
//			return;
//		}
	}

	public static void setDealNetwork(DealNetwork dealNetwork)
	{
	}
}