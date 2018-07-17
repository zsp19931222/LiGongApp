package yh.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Service1 extends Service
{
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return START_STICKY;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		if (!new ServicesTools().isRunning(this, ""))
		{
			
		}
	}

	@Override
	public void onDestroy()
	{
		this.startService(new Intent(this, yh.app.services.Service2.class));
		super.onDestroy();
	}

}
