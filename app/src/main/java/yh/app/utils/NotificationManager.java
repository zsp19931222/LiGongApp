package yh.app.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.body.Body;
import com.example.jpushdemo.body.BodyAdd;
import com.example.jpushdemo.body.BodyChat;
import com.example.jpushdemo.body.BodyPush;
import com.example.jpushdemo.body.BodyReadd;
import com.example.jpushdemo.helper.Receiver;

import android.os.Handler;
import android.os.Message;
import org.androidpn.push.Constants;
import yh.app.notification.Notification1;
import yh.app.tool.AllATSSS;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class NotificationManager
{
	private Receiver receiver = new Receiver();
	
	public static void receive(String noticeID)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("noticeID", noticeID);
		map.put("userid", Constants.number);
		AllATSSS at = new AllATSSS(_链接地址导航.PUSH.推送反馈接口.getUrl(), new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
			}
		}, map, AllATSSS.POST);
		at.executeOnExecutor(Executors.newCachedThreadPool());
	}

	public void dealOffine(String result)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(result);
			if (null != jsonObject && CodeManage.NET_SUCCESS.equals(jsonObject.getString("code")))
			{
				saveChat(jsonObject.getJSONObject("content").getJSONObject("chat").getJSONArray("content"));
				saveAdd(jsonObject.getJSONObject("content").getJSONObject("add").getJSONArray("content"));
				saveReadd(jsonObject.getJSONObject("content").getJSONObject("readd").getJSONArray("content"));
				savePush(jsonObject.getJSONObject("content").getJSONObject("push").getJSONArray("content"));
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void saveChat(JSONArray chat)
	{
		// for (int i = 0; i < chat.length(); i++)
		// try
		// {
		// // fqr, message, fssj, code, isRead, userid
		// new Receiver().saveChat(new BodyChat(JsonTools.getString(chat.getJSONObject(i), "20141029", new String[]
		// {
		// "fqr", "message", "fssj", "code", "fqr", "dsfhdsjkfhsjk"
		// })));
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		for (int i = 0; chat != null && i < chat.length(); i++)
		{
			try
			{
				BodyChat body = (BodyChat) receiver.getBodyByBundle(chat.getJSONObject(i));
				receiver.saveChat(body);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void saveAdd(JSONArray add)
	{
		for (int i = 0; add != null && i < add.length(); i++)
		{
			try
			{
				BodyAdd body = (BodyAdd) receiver.getBodyByBundle(add.getJSONObject(i));
				receiver.saveAdd(body);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void saveReadd(JSONArray readd)
	{
		for (int i = 0; readd != null && i < readd.length(); i++)
		{
			try
			{
				BodyReadd body = (BodyReadd) receiver.getBodyByBundle(readd.getJSONObject(i));
				receiver.saveReadd(body);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void savePush(JSONArray push)
	{
		// for (int i = 0; i < jsonArray.length(); i++)
		// {
		// BodyPush body = null;
		// try
		// {
		// body = new BodyPush(JsonTools.getString(jsonArray.getJSONObject(i), new String[]
		// {
		// "id", "message", "fssj", "id", "false", "jsr", "code", "url", "func_id", "title"
		// }));
		// } catch (Exception e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// new SqliteHelper().execSQL("replace into tzgg(tzggid,title,message,fssj,isread,type,url,func_id,userid) values(?,?,?,?,?,?,?,?,?)", new Object[]
		// {
		// body.getId(), body.getTitle(), body.getMessage(), body.getFssj(), body.getIsread(), body.getCode(), body.getUrl(), body.getFunc_id(), body.getUserid()
		// });
		// }
		for (int i = 0; push != null && i < push.length(); i++)
		{
			try
			{
				BodyPush body = (BodyPush) receiver.getBodyByBundle(push.getJSONObject(i));
				receiver.savePush(body);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static List<Map<String, String>> getOffineNotification(String userid)
	{
		new ThreadPoolManage().addPostTask(_链接地址导航.PUSH.推送离线接口.getUrl(), MapTools.buildMap(new String[][]
		{
				{
						"userid", Constants.number
				}
		}), new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				String offineNotification = msg.getData().getString("msg");
				try
				{
					JSONArray json = new JSONArray(offineNotification);
					for (int i = 0; i < json.length(); i++)
					{
						Notification1.buildNotification_push(json.getJSONObject(i).getString("id"), json.getJSONObject(i).getString("title"), json.getJSONObject(i).getString("message"), Ticket.getFunctionAction(json.getJSONObject(i).getString("func_id")), ExampleApplication.getAppPackage(), true, true, true, Notification1.NTIFICATION_TYPE_PUSH);
					}
				} catch (JSONException e)
				{
					e.printStackTrace();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		// at.execute();
		return null;
	}
}
