package yh.app.function.UI;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import yh.app.appstart.lg.R;
import org.androidpn.push.Constants;
import yh.app.function.FriendCycle;
import yh.app.quanzitool.QuanZiXiaoXiItem;
import yh.app.tool.SqliteHelper;

public class _圈子_消息
{
	private Context context;
	private View parent;

	public _圈子_消息(Context context, View parent)
	{
		this.context = context;
		this.parent = parent;
	}

	public void setChatedMessage()
	{
		((LinearLayout) parent.findViewById(R.id.quanzi_xiaoxi_list)).removeAllViews();
		if (getChatedMessageNum() == 0)
		{
			// 当前用户没有聊天消息 quanzi_xiaoxi_list
			parent.findViewById(R.id.sc).setVisibility(View.GONE);
			parent.findViewById(R.id.no_message_layout).setVisibility(View.VISIBLE);
			parent.findViewById(R.id.no_message_layout).setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					((FriendCycle) context).clickViewPager(3);
				}
			});
		} else
		{
			parent.findViewById(R.id.sc).setVisibility(View.VISIBLE);
			parent.findViewById(R.id.no_message_layout).setVisibility(View.GONE);
			getAllMessageMap();
		}

	}

	public void getAllMessageMap()
	{
		addMessage(new SqliteHelper().rawQuery("select max(fssj) fssj,message, friend_id from lt where userid=? group by friend_id order by fssj desc", new String[]
		{
				Constants.number
		}));
	}

	public void addMessage(List<Map<String, String>> list)
	{
		for (int i = 0; i < list.size(); i++)
			new QuanZiXiaoXiItem(context, (LinearLayout) parent.findViewById(R.id.quanzi_xiaoxi_list), list.get(i).get("friend_id")).setContent(list.get(i).get("message")).setWDTSBJ().setDate(list.get(i).get("fssj")).setName();
	}

	public void getChatedMessageLayout()
	{
		List<Map<String, String>> ChatedMessage = getChatedMessage();
		for (int i = 0; i < ChatedMessage.size(); i++)
		{
			setChatedMessageLayoutSub(ChatedMessage.get(i));
		}
	}

	private void setChatedMessageLayoutSub(Map<String, String> message)
	{
		new QuanZiXiaoXiItem(context, (LinearLayout) parent.findViewById(R.id.quanzi_xiaoxi_list), message.get("friend_id")).setContent(message.get("message")).setName().setDate(message.get("fssj")).setOnClick();
	}

	public List<Map<String, String>> getChatedMessage()
	{
		return new SqliteHelper().rawQuery(String.format("select a.friend_id,a.message,b.name from lt as a,friend as b where a.userid='%s' and a.friend_id = b.friend_id", Constants.number));
	}

	private int getChatedMessageNum()
	{
		try
		{
			return new SqliteHelper().rawQuery(String.format("select * from lt where userid='%s'", Constants.number)).size();
		} catch (Exception e)
		{
			return 0;
		}

	}
}
