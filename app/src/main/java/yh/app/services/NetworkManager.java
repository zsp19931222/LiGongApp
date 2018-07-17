package yh.app.services;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class NetworkManager
{
	
	private NetworkService myReceiver;
	public void startNetworkService(Context context)
	{
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		myReceiver = new NetworkService();
		context.registerReceiver(myReceiver, filter);
	}
}