package com.example.jpushdemo.helper;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jpushdemo.ExampleApplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.androidpn.push.Constants;
import yh.app.notification.NotificationTools;
import yh.app.quanzi.lxr.AddFriendHelper;
import yh.app.quanzitool.pinyin;
import yh.app.tool.CurrentActivity;
import 云华.智慧校园.工具.IsNull;

@SuppressWarnings("unused")
public class JPushReceiverOpen
{
	private String content, extra, id, code, fssj, fqr, title, url, func_id, ticket, name, sfty;
	private static Object obj = "s";

	public JPushReceiverOpen(Bundle bundle)
	{
		initDate(bundle);
	}

	private void initDate(Bundle bundle)
	{
		content = bundle.getString("cn.jpush.android.ALERT");
		extra = bundle.getString("cn.jpush.android.EXTRA");
		title = bundle.getString("cn.jpush.android.NOTIFICATION_CONTENT_TITLE");
		JSONObject jso;
		try
		{
			jso = new JSONObject(extra);
			id = getDate(jso, "messageID");
			code = getDate(jso, "code");
			if (code.equals("102"))
			{
				url = getDate(jso, "url");
			}
			fssj = getDate(jso, "fssj");
			fqr = getDate(jso, "fqr");
			func_id = getDate(jso, "func_id");
			ticket = getDate(jso, "ticket");
			name = getDate(jso, "fqrname");
			sfty = getDate(jso, "sfty");
		} catch (JSONException e)
		{
		}
	}

	public void doit(Context context)
	{
		synchronized (obj)
		{
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (code.equals("201"))
			{
				intent.putExtra("friend_id", fqr);
				intent.setAction("yh.app.function.liaotianjiemian");
				intent.setPackage(CurrentActivity.NOW_ACTIVITY.getPackageName());
			} else if (code.equals("202"))
			{
				intent.setAction("yh.app.quanzi.sftjhy");
				intent.setPackage(CurrentActivity.NOW_ACTIVITY.getPackageName());
				intent.putExtra("message", content);
				intent.putExtra("friend_id", fqr);
				intent.putExtra("name", name);
			} else if (code.equals("203"))
			{
				NoticeAddFriend(intent);
			} else if (code.equals("101") || code.equals("102"))
			{
				String action = null;
				if (code.equals("101"))
				{
					action = "yh.app.mymessage.tzggxq";
				} else if (code.equals("102"))
				{
					action = "yh.app.web.Web";
				}
				intent.setAction(action);
				intent.setPackage(ExampleApplication.getAppPackage());
				intent.putExtra("id", id);
				intent.putExtra("url", NotificationTools.getNotificationURL(id));
			} else if (code.equals("301"))
			{
				try
				{
					new DoPush().force(ticket, CurrentActivity.NOW_ACTIVITY, title, content);
				} catch (Exception e)
				{
				}
			}
			try
			{
				context.startActivity(intent);
			} catch (Exception e)
			{
			}
		}
	}

	private String notification_content;

	private void NoticeAddFriend(Intent intent)
	{
		if (IsNull.isNotNull(name))
		{
			if ("1".equals(sfty))
			{
				notification_content = name + "与您成为好友,快开始聊天吧。";
				new AddFriendHelper().addFriend(fqr, name, pinyin.getSpells(name).substring(0, 1), Constants.number);
			} else if ("4".equals(sfty))
			{
				notification_content = name + "拒绝了您的请求。";
			}
			intent.setAction("yh.app.quanzi.tjhyxxfk");
			intent.setPackage(CurrentActivity.NOW_ACTIVITY.getPackageName());
			intent.putExtra("friend_id", fqr);
			intent.putExtra("message", notification_content);
			intent.putExtra("name", name);
		}

	}

	private String getDate(JSONObject jso, String zd)
	{
		try
		{
			String result = jso.getString(zd);
			if (result == null)
				return " ";
			else
				return result;
		} catch (Exception e)
		{
			return " ";
		}
	}

}
