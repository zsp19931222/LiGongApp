package yh.app.utils;

import android.os.Handler;
import org.androidpn.push.Constants;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;

public class ChatOffineManage
{
	public void getOffineMessage(String url, Handler handler)
	{
		new ThreadPoolManage().addPostTask(url, MapTools.buildMap(new String[][]
		{
				{
						"userid", Constants.number
				}
		}), handler);
	}
}