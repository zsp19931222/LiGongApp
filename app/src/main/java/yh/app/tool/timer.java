package yh.app.tool;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import 云华.智慧校园.工具.MapTools;

public class timer extends Thread
{
	private int time;
	private boolean run = true;
	private View v;
	private Context context;
	private String cls;
	private String pkg;
	private String[][] array;
	private Map<String, String> extra;
	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			try
			{
				v.getBackground().clearColorFilter();
			} catch (Exception e)
			{
				// TODO: handle exception
			}
			intent();
		}
	};

	public timer(View v, int time, Context context, String cls, String pkg, String[][] array)
	{
		this.cls = cls;
		this.pkg = pkg;
		this.context = context;
		this.time = time;
		this.v = v;
		this.array = array;
	}

	public timer(View v, int time, Context context, String cls, String pkg, Map<String, String> extra)
	{
		this.cls = cls;
		this.pkg = pkg;
		this.context = context;
		this.time = time;
		this.v = v;
		this.extra = extra;
	}

	public void intent()
	{
		Intent intent = new Intent();
		intent.setAction(cls);
		intent.setPackage(pkg);
		try
		{
			if (array != null)
				for (int i = 0; i < array.length; i++)
				{
					intent.putExtra(array[i][0], array[i][1]);
				}
		} catch (Exception e)
		{
		}
		if (extra != null)
		{
			Iterator<String> keys = MapTools.getMapKeys(extra);
			while (keys.hasNext())
			{
				try
				{
					String name = keys.next();
					intent.putExtra(name, extra.get(name));
				} catch (Exception e)
				{
				}
			}
		}
		try
		{
			context.startActivity(intent);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		if (run)
		{
			// ......处理比较耗时的操作
			Timer t = new Timer();
			t.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					Message msg = new Message();
					mHandler.sendMessage(msg);
				}
			}, time);

		} else
		{
			this.interrupt();
		}
	}

	public void close(boolean run)
	{
		this.run = run;
	}
}
