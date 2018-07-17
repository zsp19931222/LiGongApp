package yh.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class Service2 extends Service
{
	@Override
	public IBinder onBind(Intent arg0)
	{
		Toast.makeText(this, "Service2 onBind", Toast.LENGTH_SHORT).show();
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Toast.makeText(this, "Service2 onStartCommand", Toast.LENGTH_SHORT).show();
		return START_STICKY;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Toast.makeText(this, "Service2 onCreate", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy()
	{
		Toast.makeText(this, "Service2 onDestroy", Toast.LENGTH_SHORT).show();
		this.startService(new Intent(this, yh.app.services.Service1.class));
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId)
	{
		Toast.makeText(this, "Service2 onStart", Toast.LENGTH_SHORT).show();
		super.onStart(intent, startId);

	}

}