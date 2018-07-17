package 云华.智慧校园.工具;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import org.androidpn.push.Constants;
import yh.app.tool.Ticket;

public class DefaultPost
{
	public void doPost(String url, String function_id, Handler handler)
	{
		if (Constants.isNetworkAvailable(Constants.App_Context))
		{
			Map<String, String> params = new HashMap<String, String>();
			params.put("userid", Constants.number);
			params.put("function_id", function_id);
			params.put("ticket", Ticket.getFunctionTicket(function_id));
			params.put("version", "0");
			new ThreadPoolManage().addPostTask(url, params, handler);
		} else
		{
			handler.sendMessage(new Message());
		}
	}
}
