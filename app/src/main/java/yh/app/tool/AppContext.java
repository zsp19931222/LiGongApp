package yh.app.tool;

import com.baidu.mapapi.SDKInitializer;
import android.app.Application;

public class AppContext extends Application
{

	@Override
	public void onCreate()
	{
		super.onCreate();
		SDKInitializer.initialize(this);
	}
	
}
