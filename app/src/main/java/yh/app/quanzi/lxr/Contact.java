package yh.app.quanzi.lxr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import yh.app.appstart.lg.R;
import org.androidpn.push.Constants;
import yh.app.quanzitool.InitMrfz;
import yh.app.quanzitool.pinyin;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class Contact
{
	private Context context;
	private LinearLayout layout;

	public List<Map<String, String>> SQ_HYLB = new ArrayList<Map<String, String>>();
	public static ReadWriteLock LOCK = new ReentrantReadWriteLock();

	public Contact(Context context, View parentView)
	{
		this.context = context;
		this.layout = (LinearLayout) parentView.findViewById(R.id.quanzi_mrfz_layout);
	}

	private Handler getContact = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			try
			{
				if (new JSONObject(msg.getData().getString("msg")).getBoolean("boolean"))
				{
					SQ_HYLB = JsonTools.getListMapBtJsonArray(new JSONObject(msg.getData().getString("msg")).getJSONArray("message"));
					saveContact(SQ_HYLB);
					buildContact(new SqliteHelper().rawQuery("select * from friend where userid=?", new String[]
					{
							Constants.number
					}));
				} else
				{
					new ToastShow().show(context, "好友列表获取失败，请稍后重试");
				}
			} catch (Exception e)
			{
				new ToastShow().show(context, "好友列表获取失败，请稍后重试");
			}
		};
	};

	public void getContact(String function_id)
	{
		Map<String, String> params = MapTools.buildMap(new String[][]
		{
				{
						"userid", Constants.number
				},
				{
						"function_id", function_id
				},
				{
						"ticket", Ticket.getFunctionTicket(function_id)
				}
		});
		new ThreadPoolManage().addPostTask(_链接地址导航.DC.圈子好友列表.getUrl(), params, getContact);
	}

	private void saveContact(List<Map<String, String>> list)
	{
		try
		{
			new SqliteHelper().execSQL("delete from friend");
			for (int i = 0; i < SQ_HYLB.size(); i++)
			{
				if (IsNull.isNotNull(SQ_HYLB.get(i).get("HYBZ")))
					new SqliteHelper().execSQL("replace into friend(FRIEND_ID,name,szm,userid) values(?, ?, ?, ?)", new Object[]
					{
							SQ_HYLB.get(i).get("HYBH"), SQ_HYLB.get(i).get("HYBZ"), pinyin.getSpells(SQ_HYLB.get(i).get("HYBZ")).substring(0, 1), Constants.number
					});
				else
					new SqliteHelper().execSQL("replace into friend(FRIEND_ID,name,szm,userid) values(?, ?, ?, ?)", new Object[]
					{
							SQ_HYLB.get(i).get("HYBH"), SQ_HYLB.get(i).get("HYBH"), pinyin.getSpells(SQ_HYLB.get(i).get("HYBH")).substring(0, 1), Constants.number
					});
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}

	}

	private void buildContact(List<Map<String, String>> list)
	{
		LOCK.readLock().lock();
		try
		{
			new InitMrfz(layout, context).doInit("friend");
		} catch (Exception e)
		{
		}
		LOCK.readLock().unlock();
	}
}
