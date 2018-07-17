package yh.app.wisdomclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import yh.app.appstart.lg.R;
public class zhktXSLB
{
	private List<Button> button;
	private Timer t = new Timer();
	private List<ImageView> imagelist;

	// private boolean state = true;

	public zhktXSLB(List<Button> button, List<ImageView> imagelist)
	{
		this.button = button;
		this.imagelist = imagelist;
	}

	// 已点名学生列表
	public void getYDMXSLB()
	{
		t.schedule(new myTimerTask(getYDMXS, "xkkh"), 4 * 1000, 4 * 1000);
	}

	// 关闭线程
	public void close()
	{
		t.cancel();
	}

	public Handler getYDMXS = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 0:
				close();
				break;
			case 1:
				gengxin(msg);
				break;
			default:
				break;
			}
		}
	};

	private void gengxin(Message msg)
	{
		String result = msg.getData().toString();
		result = result.substring(9, result.length() - 2);
		JSONArray jsa = null;
		try
		{
			jsa = new JSONArray(result);

		} catch (Exception e)
		{
		}
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < jsa.length(); i++)
		{
			try
			{
				map.put(getDate(jsa.getJSONObject(i), "XH"), "1");
			} catch (JSONException e)
			{
			}
		}
		for (int i = 0; i < button.size(); i++)
		{
			String xh = button.get(i).getText().toString();
			if (map.get(xh).equals("1"))
			{
				button.get(i).setBackgroundResource(R.drawable.zhkt_dm_yes);
				imagelist.get(i).setBackgroundResource(R.drawable.dianming_yes);
				imagelist.get(i).setTag("1");
			} else
				imagelist.get(i).setTag("0");
		}
	}

	private static String getDate(JSONObject jso, String name)
	{
		try
		{
			return jso.getString("XH").toString();
		} catch (JSONException e)
		{
			return "0";
		}
	}

	class myTimerTask extends TimerTask
	{
		private Handler handler;
		private String xkkh;

		public myTimerTask(Handler handler, String xkkh)
		{
			this.handler = handler;
			this.xkkh = xkkh;
		}

		@Override
		public void run()
		{
			Message msg = new Message();
			// 刷新操作
			zhktAT at = new zhktAT(4, handler);
			at.execute(xkkh);
			msg.what = 1;
		}
	}
}
