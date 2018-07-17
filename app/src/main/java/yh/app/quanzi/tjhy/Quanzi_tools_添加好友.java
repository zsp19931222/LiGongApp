package yh.app.quanzi.tjhy;

import android.os.Handler;
import android.os.Message;
import 云华.智慧校园.工具.BundleTools;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class Quanzi_tools_添加好友
{
	public int pagesize = 10, pagenow = 1;
	public Object obj = new Object();

	public Quanzi_tools_添加好友()
	{
	}

	public void _搜索好友(String userid, String function_id, String ticket, String cxtj, int pagenow, Handler handler)
	{
		synchronized (obj)
		{
			new ThreadPoolManage().addPostTask(_链接地址导航.DC.搜索好友.getUrl(), MapTools.buildMap(new String[][]
			{
					{
							"userid", userid
					},
					{
							"function_id", function_id
					},
					{
							"ticket", ticket
					},
					{
							"cxtj", cxtj
					},
					{
							"pagesize", pagesize + ""
					},
					{
							"pagenow", pagenow + ""
					}
			}), handler);
		}
	}

	public void _搜索上一页好友(String userid, String function_id, String ticket, String cxtj, Handler handler)
	{
		if (pagenow > 2)
		{
			pagenow = pagenow - 1;
			_搜索好友(userid, function_id, ticket, cxtj, pagenow, handler);
		} else
		{
			Message msg = new Message();
			msg.setData(BundleTools.getBundleToMap(MapTools.buildMap(new String[][]
			{
					{
							"message", "已经到互联网尽头了，换个条件再试试吧"
					},
					{
							"code", CodeManage.ERROR
					}
			})));
			handler.sendMessage(msg);
		}
	}

	public void _搜索下一页好友(String userid, String function_id, String ticket, String cxtj, Handler handler)
	{
		pagenow = pagenow + 1;
		_搜索好友(userid, function_id, ticket, cxtj, pagenow, handler);
	}
}
