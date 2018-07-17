package yh.app.utils;

import android.os.Handler;
import android.os.Message;

public class HandlerHelper
{
	public void sendWhat(Handler handler, int what)
	{
		Message msg = new Message();
		msg.what = what;
		handler.sendMessage(msg);
	}
}
